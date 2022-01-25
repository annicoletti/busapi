package br.com.nicoletti.busapi.beans.lottery.to;

public class LotteryNumberTO implements Comparable<LotteryNumberTO> {

	private Integer number;

	private Integer amount;

	public LotteryNumberTO() {
	}

	public LotteryNumberTO(Integer number, Integer amount) {
		this.number = number;
		this.amount = amount;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "LotteryNumberTO [number=" + number + ", amount=" + amount + "]\n";
	}

	@Override
	public int compareTo(LotteryNumberTO o) {
		if (this.amount > o.amount) {
			return -1;
		} else if (this.amount < o.amount) {
			return 1;
		}
		return 0;
	}

}
