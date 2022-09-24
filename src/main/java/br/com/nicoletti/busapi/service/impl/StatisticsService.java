package br.com.nicoletti.busapi.service.impl;

import java.text.ParseException;
import java.util.List;

import br.com.nicoletti.busapi.beans.lottery.enums.LotteryTypeGame;
import br.com.nicoletti.busapi.beans.lottery.to.CalculateTableTO;
import br.com.nicoletti.busapi.beans.lottery.to.CalculatedResultTO;
import br.com.nicoletti.busapi.beans.lottery.to.ChartTO;

public interface StatisticsService {

	CalculatedResultTO averageRuffleNumbers(String gameType, String startsIn) throws ParseException;

	ChartTO getDataFromMonth(LotteryTypeGame lotteryTypeGame);

	ChartTO getFullChartData(LotteryTypeGame lotteryTypeGame);

	List<CalculateTableTO> getRatioCoefficient(LotteryTypeGame enum1);

}
