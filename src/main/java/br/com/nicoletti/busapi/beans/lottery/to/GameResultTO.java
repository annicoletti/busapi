package br.com.nicoletti.busapi.beans.lottery.to;

import java.util.Date;
import java.util.List;

import br.com.nicoletti.busapi.beans.lottery.enums.LotteryTypeGame;

public class GameResultTO {

	private Date calculationDate;

	private LotteryTypeGame gameType;

	private Integer previousContestNumber;

	private Integer nextContestNumber;

	private Integer number;

	private List<Integer> tensRaffledOrderRaffle;

	private List<ApportionmentPrizeTO> apportionmentPrize;

	public Date getCalculationDate() {
		return calculationDate;
	}

	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = calculationDate;
	}

	public LotteryTypeGame getGameType() {
		return gameType;
	}

	public void setGameType(LotteryTypeGame gameType) {
		this.gameType = gameType;
	}

	public Integer getPreviousContestNumber() {
		return previousContestNumber;
	}

	public void setPreviousContestNumber(Integer previousContestNumber) {
		this.previousContestNumber = previousContestNumber;
	}

	public Integer getNextContestNumber() {
		return nextContestNumber;
	}

	public void setNextContestNumber(Integer nextContestNumber) {
		this.nextContestNumber = nextContestNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Integer> getTensRaffledOrderRaffle() {
		return tensRaffledOrderRaffle;
	}

	public void setTensRaffledOrderRaffle(List<Integer> tensRaffledOrderRaffle) {
		this.tensRaffledOrderRaffle = tensRaffledOrderRaffle;
	}

	public List<ApportionmentPrizeTO> getApportionmentPrize() {
		return apportionmentPrize;
	}

	public void setApportionmentPrize(List<ApportionmentPrizeTO> apportionmentPrize) {
		this.apportionmentPrize = apportionmentPrize;
	}

}
