package br.com.nicoletti.busapi.beans.lottery.to;

import java.util.List;

public class CalculateTableTO {

	private static final String TOTALS = "totals";

	private List<RowCalculateTableTO> rows;

	public CalculateTableTO() {
	}

	public CalculateTableTO(List<RowCalculateTableTO> rows) {
		this.rows = rows;
	}

	public List<RowCalculateTableTO> getRows() {
		return rows;
	}

	public void setRows(List<RowCalculateTableTO> rows) {
		this.rows = rows;
	}

	public RowCalculateTableTO totals() {
		String name = TOTALS;
		Double totalX = 0.0;
		Double totalY = 0.0;

		for (RowCalculateTableTO row : this.rows) {
			totalX += row.getX();
			totalY += row.getY();
		}
		return new RowCalculateTableTO(name, totalX, totalY);
	}

}
