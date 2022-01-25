package br.com.nicoletti.busapi.beans.lottery.to;

import java.util.List;

public class GameResultReportTO {

	private String typeGame;

	private Integer totalContest;

	private List<Integer> contestsWinners;

	private List<TotalPrizeTO> totalPrizes;

	public String getTypeGame() {
		return typeGame;
	}

	public void setTypeGame(String typeGame) {
		this.typeGame = typeGame;
	}

	public Integer getTotalContest() {
		return totalContest;
	}

	public void setTotalContest(Integer totalContest) {
		this.totalContest = totalContest;
	}

	public List<Integer> getContestsWinners() {
		return contestsWinners;
	}

	public void setContestsWinners(List<Integer> contestsWinners) {
		this.contestsWinners = contestsWinners;
	}

	public List<TotalPrizeTO> getTotalPrizes() {
		return totalPrizes;
	}

	public void setTotalPrizes(List<TotalPrizeTO> totalPrizes) {
		this.totalPrizes = totalPrizes;
	}

}
