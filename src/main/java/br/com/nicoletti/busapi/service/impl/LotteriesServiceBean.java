package br.com.nicoletti.busapi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.nicoletti.busapi.beans.lottery.enums.LotteryTypeGame;
import br.com.nicoletti.busapi.beans.lottery.to.ApportionmentPrizeOnlineTO;
import br.com.nicoletti.busapi.beans.lottery.to.ApportionmentPrizeTO;
import br.com.nicoletti.busapi.beans.lottery.to.CalculatedResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.GameResultCheckerTO;
import br.com.nicoletti.busapi.beans.lottery.to.GameResultOnlineTO;
import br.com.nicoletti.busapi.beans.lottery.to.GameResultReportTO;
import br.com.nicoletti.busapi.beans.lottery.to.GameResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.LotteryNumberTO;
import br.com.nicoletti.busapi.beans.lottery.to.RaffleTO;
import br.com.nicoletti.busapi.beans.lottery.to.TotalPrizeTO;
import br.com.nicoletti.busapi.beans.lottery.vo.ApportionmentPrizeVO;
import br.com.nicoletti.busapi.beans.lottery.vo.BetVO;
import br.com.nicoletti.busapi.beans.lottery.vo.GameResultVO;
import br.com.nicoletti.busapi.beans.lottery.vo.RaffleNumberVO;
import br.com.nicoletti.busapi.beans.repository.BetDAO;
import br.com.nicoletti.busapi.beans.repository.GameResultDAO;
import br.com.nicoletti.busapi.beans.to.ResponseFailedTO;
import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.exception.BusException;
import br.com.nicoletti.busapi.service.api.LoteriasService;
import br.com.nicoletti.busapi.service.api.RestService;
import br.com.nicoletti.busapi.utils.BusExceptions;
import br.com.nicoletti.busapi.utils.LoteriasConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LotteriesServiceBean implements LoteriasService {

	private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private RestService restService;

	@Autowired
	private GameResultDAO gameResultDAO;

	@Autowired
	private BetDAO betDAO;

	@Autowired
	private StatisticsService statisticsService;

	@Override
	public GameResultTO getLotteryGameResultOnline(String tipoJogo, Integer numero) throws BusException {

		try {

			String url = getUrl(tipoJogo, numero);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(HttpHeaders.COOKIE, "security=true");
			String response = restService.doSimpleGet(url, httpHeaders);

			JSONObject jsonObject = new JSONObject(response);
			if (jsonObject.has("erro")) {
				String message = jsonObject.getString("mensagem");
				throw new BusException(BusExceptions.LOTTERY_SERVICE_ERROR_ONLINE_RESULT_RETURNED_ERROR,
						new String[] { String.valueOf(numero), tipoJogo, message });
			}

			return parseToGameResult(jsonObject);

		} catch (BusException e) {
			throw e;

		} catch (JSONException e) {
			e.printStackTrace();
			throw new BusException(BusExceptions.LOTTERY_SERVICE_ERROR_PARSE_JSON,
					new String[] { String.valueOf(e.getMessage()) });

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusException(BusExceptions.LOTTERY_SERVICE_UNKNOW_ERROR,
					new String[] { String.valueOf(e.getMessage()) });
		}
	}

	@Override
	public ResponseTO getSingleResultOnline(RaffleTO sorteioTO) {
		ResponseSuccessTO<GameResultTO> responseSuccessTO = new ResponseSuccessTO<>();
		ResponseFailedTO responseFailedTO = new ResponseFailedTO();

		try {

			GameResultTO gameResult = getLotteryGameResultOnline(sorteioTO.getTipoJogo(), sorteioTO.getNumero());
			responseSuccessTO.setData(gameResult);
			return responseSuccessTO;

		} catch (BusException e) {
			e.printStackTrace();
			responseFailedTO.setErrorCode(e.getCode());
			responseFailedTO.setErrorMessage(e.getMessage());
			return responseFailedTO;

		} catch (Exception e) {
			e.printStackTrace();
			responseFailedTO.setErrorCode(BusExceptions.LOTTERY_SERVICE_UNKNOW_ERROR);
			responseFailedTO.setErrorMessage("Erro inesperado. Mensagem: " + e.getMessage());
			return responseFailedTO;
		}

	}

	private GameResultTO parseToGameResult(JSONObject jsonObject) throws BusException {
		GameResultOnlineTO out = new GameResultOnlineTO();

		try {

			List<ApportionmentPrizeTO> apportionmentPrizeTOs = new ArrayList<ApportionmentPrizeTO>();

			JSONArray jsonArray = jsonObject.getJSONArray("listaRateioPremio");
			jsonArray.forEach(item -> {
				JSONObject jsonPrize = (JSONObject) item;
				ApportionmentPrizeOnlineTO to = new ApportionmentPrizeOnlineTO();
				to.setTrackDescription(jsonPrize.getString("descricaoFaixa"));
				to.setNumberWinners(jsonPrize.getInt("numeroDeGanhadores"));
				to.setPrizeValue(jsonPrize.getBigDecimal("valorPremio"));
				to.setTrack(jsonPrize.getInt("faixa"));
				apportionmentPrizeTOs.add(to);
			});

			String[] cityState = splitValueFromString(jsonObject.getString("nomeMunicipioUFSorteio"));
			String municipio = cityState.length >= 1 && !cityState[0].isBlank() ? cityState[0] : "";
			String uf = cityState.length >= 2 && !cityState[1].isBlank() ? cityState[1] : "";

			LotteryTypeGame gameType = LotteryTypeGame.toEnum(jsonObject.getString("tipoJogo"));

			out.setAccumulated(jsonObject.getBoolean("acumulado"));
			out.setAccumulatedValueNextContest(jsonObject.getBigDecimal("valorAcumuladoProximoConcurso"));
			out.setAccumulatedValueSpecialContest(jsonObject.getBigDecimal("valorAcumuladoConcursoEspecial"));
			out.setCalculationDate(SDF.parse(jsonObject.getString("dataApuracao")));
			out.setApportionmentPrize(apportionmentPrizeTOs);
			out.setDateNextContest(jsonObject.getString("dataProximoConcurso"));
			out.setEstimatedValueForNextCompetition(jsonObject.getBigDecimal("valorEstimadoProximoConcurso"));
			out.setGameType(gameType);
			out.setNameCityLottery(municipio);
			out.setNameStateLottery(uf);
			out.setNextContestNumber(jsonObject.getInt("numeroConcursoProximo"));
			out.setNumber(jsonObject.getInt("numero"));
			out.setPlaceLottery(jsonObject.getString("localSorteio").trim());
			out.setPreviousContestNumber(jsonObject.getInt("numeroConcursoAnterior"));
			out.setTensList(parseToList(jsonObject.getJSONArray("listaDezenas")));
			out.setTensRaffledOrderRaffle(parseToList(jsonObject.getJSONArray("dezenasSorteadasOrdemSorteio")));
			out.setValueCollected(jsonObject.getBigDecimal("valorArrecadado"));

			return out;

		} catch (JSONException e) {
			e.printStackTrace();
			throw new BusException(BusExceptions.LOTTERY_SERVICE_ERROR_PARSE_JSON,
					new String[] { String.valueOf(e.getMessage()) });

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusException(BusExceptions.LOTTERY_SERVICE_UNKNOW_ERROR,
					new String[] { String.valueOf(e.getMessage()) });
		}

	}

	private String[] splitValueFromString(String string) {
		String[] split = string.split(",");
		for (String s : split) {
			s.toLowerCase().strip();
		}
		return split;
	}

	private List<Integer> parseToList(JSONArray jsonArray) {
		List<Integer> l = new ArrayList<Integer>();
		jsonArray.forEach(i -> {
			l.add(Integer.parseInt((String) i));
		});
		return l;
	}

	@Override
	public GameResultVO parseGameResultTOtoVO(GameResultTO to) {

		GameResultVO gameResultVO = new GameResultVO();
		gameResultVO.setId(null);
		gameResultVO.setContest(to.getNumber());
		gameResultVO.setApportionmentPrizes(convertApportionmentFromTO(to, gameResultVO));
		gameResultVO.setDrawDate(to.getCalculationDate());
		gameResultVO.setGameType(to.getGameType().getName());
		gameResultVO.setNextContestNumber(to.getNextContestNumber());
		gameResultVO.setPreviousContestNumber(to.getPreviousContestNumber());
		gameResultVO.setTens(convertTensListFromTO(to, gameResultVO));

		return gameResultVO;

	}

	private List<RaffleNumberVO> convertTensListFromTO(GameResultTO to, GameResultVO gameResultVO) {

		List<RaffleNumberVO> list = new ArrayList<>();
		int sequence = 1;
		for (Integer number : to.getTensRaffledOrderRaffle()) {
			RaffleNumberVO raffleNumberVO = new RaffleNumberVO();
			raffleNumberVO.setId(null);
			raffleNumberVO.setGameResult(gameResultVO);
			raffleNumberVO.setNumber(number);
			raffleNumberVO.setSequence(sequence++);
			list.add(raffleNumberVO);
		}

		return list;
	}

	private List<ApportionmentPrizeVO> convertApportionmentFromTO(GameResultTO to, GameResultVO id) {

		List<ApportionmentPrizeVO> appoList = new ArrayList<>();
		for (ApportionmentPrizeTO prize : to.getApportionmentPrize()) {
			ApportionmentPrizeVO apportionmentPrizeVO = new ApportionmentPrizeVO();
			apportionmentPrizeVO.setGameResultVO(id);
			apportionmentPrizeVO.setPrizeValue(prize.getPrizeValue());
			apportionmentPrizeVO.setTrack(prize.getTrack());
			apportionmentPrizeVO.setWinners(prize.getNumberWinners());
			appoList.add(apportionmentPrizeVO);
		}

		return appoList;
	}

	@Override
	public ResponseTO getSingleResultCached(RaffleTO sorteioTO) {
		ResponseSuccessTO<GameResultTO> responseSuccessTO = new ResponseSuccessTO<>();
		ResponseFailedTO responseFailedTO = new ResponseFailedTO();

		try {

			GameResultTO gameResult = getLotteryGameResultCached(sorteioTO.getTipoJogo(), sorteioTO.getNumero());
			responseSuccessTO.setData(gameResult);
			return responseSuccessTO;

		} catch (BusException e) {
			e.printStackTrace();
			responseFailedTO.setErrorCode(e.getCode());
			responseFailedTO.setErrorMessage(e.getMessage());
			return responseFailedTO;

		} catch (Exception e) {
			e.printStackTrace();
			responseFailedTO.setErrorCode(BusExceptions.LOTTERY_SERVICE_UNKNOW_ERROR);
			responseFailedTO.setErrorMessage("Erro inesperado. Mensagem: " + e.getMessage());
			return responseFailedTO;
		}

	}

	private GameResultTO getLotteryGameResultCached(String tipoJogo, Integer numero) throws BusException {

		try {

			GameResultVO result = gameResultDAO.findByGameTypeAndContest(tipoJogo, numero);

			if (result == null) {
				throw new BusException(BusExceptions.LOTTERY_SERVICE_ERROR_GAME_NOT_FOUND,
						new String[] { String.valueOf(numero), tipoJogo });
			}

			return parseToGameResult(result);

		} catch (BusException e) {
			throw e;

		} catch (JSONException e) {
			e.printStackTrace();
			throw new BusException(BusExceptions.LOTTERY_SERVICE_ERROR_PARSE_JSON,
					new String[] { String.valueOf(e.getMessage()) });

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusException(BusExceptions.LOTTERY_SERVICE_UNKNOW_ERROR,
					new String[] { String.valueOf(e.getMessage()) });
		}
	}

	private GameResultTO parseToGameResult(GameResultVO vo) throws BusException {

		try {
			GameResultTO out = new GameResultTO();

			out.setCalculationDate(vo.getDrawDate());
			out.setGameType(LotteryTypeGame.toEnum(vo.getGameType()));
			out.setNextContestNumber(vo.getNextContestNumber());
			out.setNumber(vo.getContest());
			out.setPreviousContestNumber(vo.getPreviousContestNumber());
			out.setTensRaffledOrderRaffle(parseTensTO(vo.getTens()));
			out.setApportionmentPrize(parsePrizeTO(vo.getApportionmentPrizes()));

			return out;

		} catch (BusException e) {
			throw new BusException(BusExceptions.LOTTERY_SERVICE_GAME_TYPE_NOT_FOUND,
					new String[] { vo.getGameType() });
		}
	}

	private List<ApportionmentPrizeTO> parsePrizeTO(List<ApportionmentPrizeVO> apportionmentPrizes) {
		List<ApportionmentPrizeTO> out = new ArrayList<>();

		apportionmentPrizes.forEach(item -> {
			ApportionmentPrizeTO to = new ApportionmentPrizeTO();
			to.setNumberWinners(item.getWinners());
			to.setPrizeValue(item.getPrizeValue());
			to.setTrack(item.getTrack());
			out.add(to);
		});

		return out;
	}

	private List<Integer> parseTensTO(List<RaffleNumberVO> tens) {
		List<Integer> out = new ArrayList<>();

		tens.forEach(item -> {
			out.add(item.getNumber());
		});

		return out;
	}

	@Override
	public String getUrl(String tipoJogo, Integer numeroSorteio) throws BusException {

		String path = null;
		LotteryTypeGame game = LotteryTypeGame.toEnum(tipoJogo);

		if (LotteryTypeGame.LOTOFACIL.equals(game)) {
			path = LoteriasConstants.PATH_LOTOFACIL;

		} else if (LotteryTypeGame.MEGASENA.equals(game)) {
			path = LoteriasConstants.PATH_MEGASENA;

		} else {
			throw new BusException(BusExceptions.LOTTERY_SERVICE_GAME_TYPE_NOT_FOUND, new String[] { tipoJogo });
		}

		if (numeroSorteio != null) {
			path = String.format(path.concat(LoteriasConstants.FILTER), numeroSorteio);
		}

		String url = LoteriasConstants.SERVER.concat(path);

		return url;
	}

	@Override
	public ResponseTO getMostRepeatedNumbers(Map<String, Object> params) {
		ResponseSuccessTO<CalculatedResultTO> responseSuccessTO = new ResponseSuccessTO<>();
		ResponseFailedTO responseFailedTO = new ResponseFailedTO();

		try {

			String gameType = (String) params.get("gameType");
			String startsIn = (String) params.get("startsIn");

			CalculatedResultTO resultTO = statisticsService.averageRuffleNumbers(gameType, startsIn);

			responseSuccessTO.setData(resultTO);
			return responseSuccessTO;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseFailedTO.setErrorCode(BusExceptions.LOTTERY_SERVICE_UNKNOW_ERROR);
			responseFailedTO.setErrorMessage("Erro inesperado. Mensagem: " + e.getMessage());
			return responseFailedTO;
		}

	}

	@Override
	public ResponseTO generateRaffleNumbers(Map<String, Object> params) {
		ResponseSuccessTO<List<Set<Integer>>> success = new ResponseSuccessTO<>();

		try {

			LotteryTypeGame gameType = LotteryTypeGame
					.toEnum((String) params.get(LoteriasConstants.REQUEST_PARAMS_GAME_TYPE));
			Integer playWith = (Integer) params.get(LoteriasConstants.REQUEST_PARAMS_PLAY_WITH);
			Integer numbersToGame = playWith != null && playWith > 0 ? playWith : gameType.getStandardQuantity();
			String startsIn = (String) params.get(LoteriasConstants.REQUEST_PARAMS_STARTS_IN);

			CalculatedResultTO raffleResult = statisticsService.averageRuffleNumbers(gameType.name(), startsIn);

			List<LotteryNumberTO> numbers = raffleResult.getNumbers();
			Set<Integer> result = new TreeSet<>();

			if (raffleResult.getNumbers().size() > 0) {
				for (int i = 0; i < numbersToGame; i++) {
					LotteryNumberTO lotteryNumberTO = numbers.get(i);
					result.add(lotteryNumberTO.getNumber());
				}
			}

			success.setData(Arrays.asList(result));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}

	@Override
	public ResponseTO checkResult(Map<String, Object> params) {
		ResponseSuccessTO<List<GameResultCheckerTO>> out = new ResponseSuccessTO<>();
		List<GameResultCheckerTO> data = resultChecker(params);
		out.setData(data);
		return out;
	}

	/**
	 * @param params
	 * @return
	 * @throws BusException
	 */
	@SuppressWarnings("unchecked")
	private List<GameResultCheckerTO> resultChecker(Map<String, Object> params) {
		List<GameResultCheckerTO> data = new ArrayList<>();

		try {
			LotteryTypeGame gameType = LotteryTypeGame
					.toEnum((String) params.get(LoteriasConstants.REQUEST_PARAMS_GAME_TYPE));
			Integer contest = (Integer) params.get(LoteriasConstants.REQUEST_PARAMS_CONTEST_NUMBER);
			List<Integer> numbers = (List<Integer>) params.get(LoteriasConstants.REQUEST_PARAMS_NUMBERS_FOR_CHECK);

			if (contest != null) {
				GameResultVO resultOfGame = gameResultDAO.findByGameTypeAndContest(gameType.getName(), contest);
				GameResultCheckerTO checkResult = verifyResultOfEachGame(resultOfGame, numbers);
				data.add(checkResult);

			} else {
				List<GameResultVO> allResults = gameResultDAO.findAllByGameType(gameType.getName());
				for (GameResultVO resultOfGame : allResults) {
					GameResultCheckerTO checkResult = verifyResultOfEachGame(resultOfGame, numbers);
					data.add(checkResult);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}

	private GameResultCheckerTO verifyResultOfEachGame(GameResultVO resultOfGame, List<Integer> numbers) {
		GameResultCheckerTO out = new GameResultCheckerTO();
		out.setIsWinner(Boolean.FALSE);

		try {

			Set<Integer> userNumbers = new TreeSet<>();
			Set<Integer> resultNumbers = new TreeSet<>();
			LotteryTypeGame typeGame = LotteryTypeGame.toEnum(resultOfGame.getGameType());

			numbers.forEach(n -> {
				userNumbers.add(n);
			});

			resultOfGame.getTens().forEach(t -> {
				resultNumbers.add(t.getNumber());
			});

			Integer correct = 0;
			Map<Integer, Boolean> checkResult = new TreeMap<>();

			for (Integer n : resultNumbers) {
				Boolean check = Boolean.FALSE;
				if (userNumbers.contains(n)) {
					check = Boolean.TRUE;
					if (++correct >= typeGame.getStandardQuantity()) {
						out.setIsWinner(Boolean.TRUE);
					}
				}

				checkResult.put(n, check);
			}

			out.setContest(resultOfGame.getContest());
			out.setResult(checkResult);
			out.setHit(correct);

		} catch (BusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public ResponseTO checkResultResume(Map<String, Object> params) {
		ResponseSuccessTO<GameResultReportTO> out = new ResponseSuccessTO<>();
		List<GameResultCheckerTO> resultChecker = resultChecker(params);

		try {
			LotteryTypeGame gameType = LotteryTypeGame
					.toEnum((String) params.get(LoteriasConstants.REQUEST_PARAMS_GAME_TYPE));

			Integer[] values = null;
			Map<Integer, Integer> prizes = new TreeMap<Integer, Integer>();
			if (gameType.equals(LotteryTypeGame.LOTOFACIL)) {
				values = new Integer[] { 15, 14, 13, 12, 11 };
			} else if (gameType.equals(LotteryTypeGame.MEGASENA)) {
				values = new Integer[] { 6, 5, 4 };
			}

			// Mostrar quantidade de premios entregue com o numero de amostra
			for (Integer v : values) {
				prizes.put(v, 0);
			}

			List<Integer> contests = new ArrayList<>();
			for (GameResultCheckerTO to : resultChecker) {
				Integer hit = to.getHit();

				if (values[0].equals(hit)) {
					contests.add(to.getContest());
				}

				Integer qtd = prizes.get(hit);
				if (qtd != null) {
					prizes.put(hit, ++qtd);
				}
			}

			List<TotalPrizeTO> totalPrizes = new ArrayList<>();
			for (Map.Entry<Integer, Integer> entry : prizes.entrySet()) {
				TotalPrizeTO prizeTO = new TotalPrizeTO();
				prizeTO.setHits(entry.getKey());
				prizeTO.setTotalContest(entry.getValue());
				totalPrizes.add(prizeTO);
			}

			GameResultReportTO reportTO = new GameResultReportTO();
			reportTO.setTypeGame(gameType.getName());
			reportTO.setContestsWinners(contests);
			reportTO.setTotalContest(resultChecker.size());
			reportTO.setTotalPrizes(totalPrizes);

			out.setData(reportTO);

		} catch (BusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public ResponseTO generateRandomNumbers(Map<String, Object> params) {
		ResponseSuccessTO<List<Set<Integer>>> response = new ResponseSuccessTO<>();

		try {

			List<Set<Integer>> out = new ArrayList<>();
			Random random = new Random();

			LotteryTypeGame gameType = LotteryTypeGame
					.toEnum((String) params.get(LoteriasConstants.REQUEST_PARAMS_GAME_TYPE));

			int quantidade = (Integer) params.getOrDefault(LoteriasConstants.REQUEST_PARAMS_GAME_NUMBERS_BET,
					gameType.getStandardQuantity());

			int maximo = gameType.getNumbersToRuffle();

			int attemp = (Integer) params.getOrDefault(LoteriasConstants.REQUEST_PARAMS_GAME_ATTEMP, 1);

			for (int i = 0; i < attemp; i++) {
				Set<Integer> numeros = new TreeSet<>();
				while (numeros.size() < quantidade) {
					int rand = random.nextInt(maximo) + 1;
					numeros.add(rand);
				}

				params.put(LoteriasConstants.REQUEST_PARAMS_NUMBERS_FOR_CHECK, List.copyOf(numeros));
				boolean isValid = Boolean.TRUE;
				List<GameResultCheckerTO> resultChecker = resultChecker(params);
				for (GameResultCheckerTO to : resultChecker) {
					if (to.getIsWinner()) {
						isValid = Boolean.FALSE;
						break;
					}
				}

				if (isValid) {
					out.add(numeros);
				} else {
					i--;
				}

			}

			response.setData(out);

		} catch (BusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseTO doBet(Map<String, Object> params) {

		try {
			LotteryTypeGame gameType = LotteryTypeGame
					.toEnum((String) params.get(LoteriasConstants.REQUEST_PARAMS_GAME_TYPE));

			boolean random = (boolean) params.getOrDefault(LoteriasConstants.REQUEST_PARAMS_GAME_RANDOM, true);

			List<Set<Integer>> data = null;

			if (random) {
				ResponseSuccessTO<?> response = (ResponseSuccessTO<?>) generateRandomNumbers(params);
				data = (List<Set<Integer>>) response.getData();
			} else {
				data = (List<Set<Integer>>) params.get("bets");
			}

			for (Set<Integer> item : data) {
				BetVO vo = parseBetToVO(item, gameType, params);
				betDAO.save(vo);
			}

		} catch (BusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private BetVO parseBetToVO(Set<Integer> item, LotteryTypeGame gameType, Map<String, Object> params) {
		BetVO vo = new BetVO();

		Integer contest = (Integer) params.get("contest");

		vo.setBet(new JSONArray(item).toString());
		vo.setContest(contest);
		vo.setDate(new Date());
		vo.setGameType(gameType.getName());
		vo.setConferred(Boolean.FALSE);
		vo.setId(null);

		return vo;
	}

}
