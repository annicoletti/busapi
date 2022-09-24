package br.com.nicoletti.busapi.beans.lottery.to;

import java.util.List;

public class ChartTO {

	private String[] column;

	private List<String[]> rows;

	public String[] getColumn() {
		return column;
	}

	public void setColumn(String[] column) {
		this.column = column;
	}

	public List<String[]> getRows() {
		return rows;
	}

	public void setRows(List<String[]> rows) {
		this.rows = rows;
	}

}
