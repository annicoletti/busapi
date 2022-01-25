package br.com.nicoletti.busapi.beans.lottery.to;

import java.util.Date;
import java.util.List;

public class CalculatedResultTO {

	private Integer totalContests;

	private String typeGame;

	private Date startsIn;

	private List<LotteryNumberTO> numbers;

	public Integer getTotalContests() {
		return totalContests;
	}

	public void setTotalContests(Integer totalContests) {
		this.totalContests = totalContests;
	}

	public String getTypeGame() {
		return typeGame;
	}

	public void setTypeGame(String typeGame) {
		this.typeGame = typeGame;
	}

	public Date getStartsIn() {
		return startsIn;
	}

	public void setStartsIn(Date startsIn) {
		this.startsIn = startsIn;
	}

	public List<LotteryNumberTO> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<LotteryNumberTO> numbers) {
		this.numbers = numbers;
	}

}
