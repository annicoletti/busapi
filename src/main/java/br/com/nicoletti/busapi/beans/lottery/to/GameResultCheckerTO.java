package br.com.nicoletti.busapi.beans.lottery.to;

import java.util.Map;

public class GameResultCheckerTO {

	private Boolean isWinner;

	private Integer contest;

	private Integer hit;

	private Map<Integer, Boolean> result;

	public Boolean getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(Boolean isWinner) {
		this.isWinner = isWinner;
	}

	public Integer getContest() {
		return contest;
	}

	public void setContest(Integer contest) {
		this.contest = contest;
	}

	public Map<Integer, Boolean> getResult() {
		return result;
	}

	public void setResult(Map<Integer, Boolean> result) {
		this.result = result;
	}

	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

}
