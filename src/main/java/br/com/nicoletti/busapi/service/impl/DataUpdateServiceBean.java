package br.com.nicoletti.busapi.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.nicoletti.busapi.beans.lottery.enums.LotteryTypeGame;
import br.com.nicoletti.busapi.beans.lottery.to.GameResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.RaffleTO;
import br.com.nicoletti.busapi.beans.lottery.vo.BetVO;
import br.com.nicoletti.busapi.beans.lottery.vo.ErrorCaseVO;
import br.com.nicoletti.busapi.beans.lottery.vo.GameResultVO;
import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.exception.BusException;
import br.com.nicoletti.busapi.repository.BetDAO;
import br.com.nicoletti.busapi.repository.ErrorCaseDAO;
import br.com.nicoletti.busapi.repository.GameResultDAO;
import br.com.nicoletti.busapi.service.api.LoteriasService;
import br.com.nicoletti.busapi.service.api.RestService;

@Service
@EnableScheduling
public class DataUpdateServiceBean {

	private final Logger log = LoggerFactory.getLogger(DataUpdateServiceBean.class);

	private static final int HOUR_IN_MILLIS = 3600000;

	@Autowired
	private LoteriasService loteriasService;

	@Autowired
	private RestService restService;

	@Autowired
	private GameResultDAO gameResultDAO;

	@Autowired
	private ErrorCaseDAO errorCaseDAO;

	@Autowired
	private BetDAO betDAO;

	@Scheduled(fixedDelay = HOUR_IN_MILLIS)
	public void fullDataUpdate() {
		log.info("Atualizando dados dos sorteios");

		try {

			refreshLottery(LotteryTypeGame.MEGASENA);
			refreshLottery(LotteryTypeGame.LOTOFACIL);

			checkBets();

			tryLoadErrorContest();

		} catch (BusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkBets() {
		log.info("VERIFICANDO JOGOS FEITOS");
		List<BetVO> bets = betDAO.findAll();

		for (BetVO bet : bets) {
			Boolean conferred = bet.getConferred();
			if (!conferred) {
				RaffleTO raffleTO = new RaffleTO();
				raffleTO.setNumero(bet.getContest());
				raffleTO.setTipoJogo(bet.getGameType());

				ResponseTO response = loteriasService.getSingleResultCached(raffleTO);
				if (response.isStatus()) {
					ResponseSuccessTO<?> res = (ResponseSuccessTO<?>) response;
					Object data = res.getData();
				}
			}
		}
	}

	private void tryLoadErrorContest() throws BusException {
		List<ErrorCaseVO> allErrors = errorCaseDAO.findAll();
		log.info("Verificação de consulta com erros. Registros com erros: {}", allErrors.size());

		for (ErrorCaseVO vo : allErrors) {
			Boolean status = downloadResultAndPersist(vo.getLotteryTypeGame(), vo.getContest());
			log.info("[{}] Resultado da consulta do concurso {} do sorteio da {}.", status, vo.getContest(),
					vo.getLotteryTypeGame());
			if (status) {
				errorCaseDAO.delete(vo);
			}
		}
	}

	private void refreshLottery(LotteryTypeGame lotteryTypeGame) throws BusException {

		String path = loteriasService.getUrl(lotteryTypeGame.getName(), null);
//		String lastResult = restService.doSimpleGetToLotteries(path);
		String lastResult = restService.doSimpleGet(path);

		JSONObject jsonLastResult = new JSONObject(lastResult);
		Integer lastNumber = jsonLastResult.getInt("numero");

		Long totalCached = gameResultDAO.countByGameType(lotteryTypeGame.getName());
		Integer remaining = lastNumber - totalCached.intValue();
		Integer contest = totalCached.intValue() + 1;

		log.info("Registros em cache: {}, serão atualizados {} registros", totalCached, remaining);

		while (remaining >= 1) {

			log.info("{} contest: {} remaining: {}", lotteryTypeGame, contest, remaining);
			downloadResultAndPersist(lotteryTypeGame.getName(), contest);

			remaining--;
			contest++;
		}

	}

	private Boolean downloadResultAndPersist(String lotteryTypeGame, Integer contest) {
		Boolean out = Boolean.FALSE;

		try {

			GameResultTO dto = loteriasService.getLotteryGameResultOnline(lotteryTypeGame, contest);
			GameResultVO vo = loteriasService.parseGameResultTOtoVO(dto);
			gameResultDAO.save(vo);
			out = Boolean.TRUE;

		} catch (BusException e) {

			ErrorCaseVO vo = errorCaseDAO.findByContest(contest);

			if (vo != null) {
				vo.setContest(contest);
				vo.setDetail(e.getMessage());
				vo.setLastAttemp(new Date());
				vo.setLotteryTypeGame(lotteryTypeGame);

				errorCaseDAO.save(vo);

			} else {
				vo = new ErrorCaseVO();
				vo.setContest(contest);
				vo.setDetail(e.getMessage());
				vo.setLastAttemp(new Date());
				vo.setLotteryTypeGame(lotteryTypeGame);

				errorCaseDAO.save(vo);
			}
		}

		return out;
	}

}
