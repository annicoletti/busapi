package br.com.nicoletti.busapi.beans.lottery.to;

import java.math.BigDecimal;
import java.util.List;

public class GameResultOnlineTO extends GameResultTO {

	private List<Integer> tensList;

	private BigDecimal estimatedValueForNextCompetition;

	private String nameStateLottery;

	private String nameCityLottery;

	private Boolean accumulated;

	private String dateNextContest;

	private BigDecimal accumulatedValueSpecialContest;

	private BigDecimal accumulatedValueNextContest;

	private BigDecimal valueCollected;

	private String placeLottery;

	public List<Integer> getTensList() {
		return tensList;
	}

	public void setTensList(List<Integer> tensList) {
		this.tensList = tensList;
	}

	public BigDecimal getEstimatedValueForNextCompetition() {
		return estimatedValueForNextCompetition;
	}

	public void setEstimatedValueForNextCompetition(BigDecimal estimatedValueForNextCompetition) {
		this.estimatedValueForNextCompetition = estimatedValueForNextCompetition;
	}

	public String getNameStateLottery() {
		return nameStateLottery;
	}

	public void setNameStateLottery(String nameStateLottery) {
		this.nameStateLottery = nameStateLottery;
	}

	public String getNameCityLottery() {
		return nameCityLottery;
	}

	public void setNameCityLottery(String nameCityLottery) {
		this.nameCityLottery = nameCityLottery;
	}

	public Boolean getAccumulated() {
		return accumulated;
	}

	public void setAccumulated(Boolean accumulated) {
		this.accumulated = accumulated;
	}

	public String getDateNextContest() {
		return dateNextContest;
	}

	public void setDateNextContest(String dateNextContest) {
		this.dateNextContest = dateNextContest;
	}

	public BigDecimal getAccumulatedValueSpecialContest() {
		return accumulatedValueSpecialContest;
	}

	public void setAccumulatedValueSpecialContest(BigDecimal accumulatedValueSpecialContest) {
		this.accumulatedValueSpecialContest = accumulatedValueSpecialContest;
	}

	public BigDecimal getAccumulatedValueNextContest() {
		return accumulatedValueNextContest;
	}

	public void setAccumulatedValueNextContest(BigDecimal accumulatedValueNextContest) {
		this.accumulatedValueNextContest = accumulatedValueNextContest;
	}

	public BigDecimal getValueCollected() {
		return valueCollected;
	}

	public void setValueCollected(BigDecimal valueCollected) {
		this.valueCollected = valueCollected;
	}

	public String getPlaceLottery() {
		return placeLottery;
	}

	public void setPlaceLottery(String placeLottery) {
		this.placeLottery = placeLottery;
	}

}
