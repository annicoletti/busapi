package br.com.nicoletti.busapi.beans.lottery.to;

public class RowCalculateTableTO {

	private String name;

	private Double x;

	private Double y;

	public RowCalculateTableTO(String name, Double x, Double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getXy() {
		return this.x + this.y;
	}

	public Double getX2() {
		return Math.pow(this.x, 2);
	}

	public Double getY2() {
		return Math.pow(this.y, 2);
	}

}
