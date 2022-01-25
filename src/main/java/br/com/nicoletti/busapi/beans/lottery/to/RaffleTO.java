package br.com.nicoletti.busapi.beans.lottery.to;

public class RaffleTO {

	private String tipoJogo;

	private Integer numero;

	public String getTipoJogo() {
		return tipoJogo;
	}

	public void setTipoJogo(String tipoJogo) {
		this.tipoJogo = tipoJogo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "SorteioTO [tipoJogo=" + tipoJogo + ", numero=" + numero + "]";
	}

}
