package br.com.nicoletti.busapi.beans.lottery.to;

import java.math.BigDecimal;

public class ApportionmentPrizeTO {

	private Integer numberWinners;

	private BigDecimal prizeValue;

	private Integer track;

	public Integer getNumberWinners() {
		return numberWinners;
	}

	public void setNumberWinners(Integer numberWinners) {
		this.numberWinners = numberWinners;
	}

	public BigDecimal getPrizeValue() {
		return prizeValue;
	}

	public void setPrizeValue(BigDecimal prizeValue) {
		this.prizeValue = prizeValue;
	}

	public Integer getTrack() {
		return track;
	}

	public void setTrack(Integer track) {
		this.track = track;
	}

}
