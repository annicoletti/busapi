package br.com.nicoletti.busapi.service.impl.avm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nicoletti.busapi.beans.lottery.enums.LotteryTypeGame;
import br.com.nicoletti.busapi.beans.lottery.to.CalculateTableTO;
import br.com.nicoletti.busapi.beans.lottery.to.CalculatedResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.ChartTO;
import br.com.nicoletti.busapi.beans.lottery.to.LotteryNumberTO;
import br.com.nicoletti.busapi.beans.lottery.to.RowCalculateTableTO;
import br.com.nicoletti.busapi.beans.lottery.vo.GameResultVO;
import br.com.nicoletti.busapi.beans.lottery.vo.RaffleNumberVO;
import br.com.nicoletti.busapi.repository.GameResultDAO;
import br.com.nicoletti.busapi.resources.dao.ResultGameDAO;
import br.com.nicoletti.busapi.service.impl.StatisticsService;

@Service
public class StatisticsServiceBean implements StatisticsService {

	private final Logger log = LoggerFactory.getLogger(StatisticsServiceBean.class);

	private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private GameResultDAO gameResultDAO;

	private ResultGameDAO resultGameDAO = new ResultGameDAO();

	@Override
	public CalculatedResultTO averageRuffleNumbers(String gameType, String startsIn) throws ParseException {
		log.info("INICIANDO O CALCULO DOS NUMEROS SORTEADOS");
		List<LotteryNumberTO> numbers;
		Date date = startsIn != null && !startsIn.isEmpty() ? SDF.parse(startsIn) : new Date(Long.MIN_VALUE);

		List<GameResultVO> vos = gameResultDAO.findAll();

		List<GameResultVO> results = new ArrayList<>();

		for (GameResultVO vo : vos) {
			if (vo.getGameType().equalsIgnoreCase(gameType) && date.before(vo.getDrawDate())) {
				results.add(vo);
			}

		}

		Map<Integer, Integer> report = countNumbersRaffle(results);

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

	/**
	 * @param results
	 * @param report
	 */
	private TreeMap<Integer, Integer> countNumbersRaffle(List<GameResultVO> results) {
		TreeMap<Integer, Integer> report = new TreeMap<>();

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

		return report;
	}

	private void countNumbersRaffle(List<GameResultVO> results, TreeMap<Integer, Integer> report) {
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
	}

	@Override
	public ChartTO getDataFromMonth(LotteryTypeGame lotteryTypeGame) {

		ChartTO chartTO = new ChartTO();
		chartTO.setColumn(null);

		Integer standardQuantity = lotteryTypeGame.getStandardQuantity();
		String[] columns = new String[standardQuantity + 1];
		columns[0] = "data";
		for (int i = 1; i <= standardQuantity; i++) {
			columns[i] = "N" + i;
		}

		// recupera o primeiro concurso para registrar a data de inicio
		GameResultVO firstContest = gameResultDAO.findByGameTypeAndContest(lotteryTypeGame.getName(), 1);
		Date initDate = firstContest.getDrawDate();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initDate);

		// define data inicial com o primeiro dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date dataInicial = calendar.getTime();

		// define data final com o ultimo dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date dataFinal = calendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		log.info("initdate: {} - endate: {}", sdf.format(dataInicial), sdf.format(dataFinal));

		List<GameResultVO> result = gameResultDAO.findByDrawDateBetweenAndGameType(initDate, dataFinal,
				lotteryTypeGame.getName());

		TreeMap<Integer, Integer> report = countNumbersRaffle(result);

		System.err.println(report);

		return null;
	}

	@Override
	public ChartTO getFullChartData(LotteryTypeGame lotteryTypeGame) {

		TreeMap<Integer, Integer> out = new TreeMap<>();

		ChartTO chartTO = new ChartTO();

		Integer standardQuantity = lotteryTypeGame.getNumbersToRuffle();
		String[] columns = new String[standardQuantity + 1];
		columns[0] = "data";
		for (int i = 1; i <= standardQuantity; i++) {
			columns[i] = "N" + i;
		}

		// recupera o primeiro concurso para registrar a data de inicio
		GameResultVO firstContest = gameResultDAO.findByGameTypeAndContest(lotteryTypeGame.getName(), 1);
		Date initDate = firstContest.getDrawDate();

		// recupera o ultimo concurso para registrar a data final do loop
		Long count = gameResultDAO.countByGameType(lotteryTypeGame.getName());
		GameResultVO currentContest = gameResultDAO.findByGameTypeAndContest(lotteryTypeGame.getName(),
				count.intValue());
		Date finish = currentContest.getDrawDate();

		Calendar stopDate = Calendar.getInstance();
		stopDate.setTime(finish);
		stopDate.set(Calendar.DAY_OF_MONTH, stopDate.getActualMinimum(Calendar.DAY_OF_MONTH));
		stopDate.set(Calendar.HOUR_OF_DAY, 0);
		stopDate.set(Calendar.MINUTE, 0);
		stopDate.set(Calendar.SECOND, 0);
		stopDate.set(Calendar.MILLISECOND, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initDate);

		List<String[]> rows = new ArrayList<>();
		while (stopDate.getTime().after(calendar.getTime())) {

			// define data inicial com o primeiro dia do mes
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date dataInicial = calendar.getTime();

			// define data final com o ultimo dia do mes
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			Date dataFinal = calendar.getTime();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			log.info("initdate: {} - endate: {}", sdf.format(dataInicial), sdf.format(dataFinal));

			// realiza consulta entre as datas já calculado
			TreeMap<Integer, Integer> result = resultGameDAO.countNumbersByDateRange(lotteryTypeGame, dataInicial,
					dataFinal);

			// calculo dos numeros sorteados
			int n = lotteryTypeGame.getNumbersToRuffle() + 1;
			String[] row = new String[n];
			row[0] = new SimpleDateFormat("MM/yyyy").format(dataInicial);
			for (int i = 1; i < n; i++) {
				int num = result.get(i) == null ? 0 : result.get(i);
				row[i] = String.valueOf(num);
			}

			// incrementa os meses para data de inicio e fim
			calendar.add(Calendar.MONTH, 1);

			log.info("row - " + new JSONArray(row).toString());

			rows.add(row);
		}

		chartTO.setColumn(columns);
		chartTO.setRows(rows);

		return chartTO;
	}

	@Override
	public List<CalculateTableTO> getRatioCoefficient(LotteryTypeGame lotteryTypeGame) {

		ChartTO chart = this.getFullChartData(lotteryTypeGame);
		List<CalculateTableTO> out = new ArrayList<CalculateTableTO>();

		Calendar calendar = Calendar.getInstance();
		int limit = lotteryTypeGame.getNumbersToRuffle() + 1;

		List<String[]> rows = chart.getRows();

		System.out.println("[[ROW]] - " + new JSONArray(rows).toString(1));

		for (int i = 0; i < rows.size(); i++) {
			List<RowCalculateTableTO> list = new ArrayList<>();

			for (int j = 1; j < limit; j++) {

				// Recuperar a data
				String[] firstRow = rows.get(i);
				String[] data = firstRow[0].split("/");
				int month = Integer.valueOf(data[0]);
				int year = Integer.valueOf(data[1]);

				// Seta data inicial
				calendar.set(year, month, 1);
				Date init = calendar.getTime();

				// Seta data final
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date end = calendar.getTime();

				Long countContest = resultGameDAO.countQuantityGameByRangeDate(init, end, LotteryTypeGame.LOTOFACIL);
				RowCalculateTableTO rowCalculate = new RowCalculateTableTO(firstRow[0], Double.valueOf(firstRow[j]),
						Double.valueOf(countContest));

				list.add(rowCalculate);
			}

			out.add(new CalculateTableTO(list));
		}

		return out;
	}

}
