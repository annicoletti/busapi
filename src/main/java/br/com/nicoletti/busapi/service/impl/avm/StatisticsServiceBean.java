package br.com.nicoletti.busapi.service.impl.avm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nicoletti.busapi.beans.lottery.to.CalculatedResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.LotteryNumberTO;
import br.com.nicoletti.busapi.beans.lottery.vo.GameResultVO;
import br.com.nicoletti.busapi.beans.lottery.vo.RaffleNumberVO;
import br.com.nicoletti.busapi.beans.repository.GameResultDAO;
import br.com.nicoletti.busapi.service.impl.StatisticsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticsServiceBean implements StatisticsService {

	private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private GameResultDAO gameResultDAO;

	@Override
	public CalculatedResultTO averageRuffleNumbers(String gameType, String startsIn) throws ParseException {
		log.info("INICIANDO O CALCULO DOS NUMEROS SORTEADOS");
		List<LotteryNumberTO> numbers;
		Date date = startsIn != null && !startsIn.isEmpty() ? SDF.parse(startsIn) : new Date(Long.MIN_VALUE);

		List<GameResultVO> vos = gameResultDAO.findAll();

		List<GameResultVO> results = new ArrayList<>();
		Map<Integer, Integer> report = new TreeMap<Integer, Integer>();

		for (GameResultVO vo : vos) {
			if (vo.getGameType().equalsIgnoreCase(gameType) && date.before(vo.getDrawDate())) {
				results.add(vo);
			}

		}

		for (GameResultVO vo : results) {
			List<RaffleNumberVO> tens = vo.getTens();
			for (RaffleNumberVO num : tens) {
				Integer number = num.getNumber();
				Integer integer = report.get(number);
				if (integer == null) {
					report.put(number, 1);
				} else {
					report.put(number, ++integer);
				}
			}
		}

		numbers = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : report.entrySet()) {
			numbers.add(new LotteryNumberTO(entry.getKey(), entry.getValue()));
		}
		Collections.sort(numbers);

		CalculatedResultTO resultTO = new CalculatedResultTO();
		resultTO.setNumbers(numbers);
		resultTO.setStartsIn(date);
		resultTO.setTypeGame(gameType);
		resultTO.setTotalContests(results.size());

		log.info("FIM DO CALCULO DOS NUMEROS SORTEADOS");
		return resultTO;
	}
}
