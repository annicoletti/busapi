package br.com.nicoletti.busapi.service.impl;

import java.text.ParseException;

import br.com.nicoletti.busapi.beans.lottery.to.CalculatedResultTO;

public interface StatisticsService {

	CalculatedResultTO averageRuffleNumbers(String gameType, String startsIn) throws ParseException;

}
