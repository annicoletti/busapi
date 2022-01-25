package br.com.nicoletti.busapi.beans.to;

public abstract class ResponseTO {

	private boolean status;

	public ResponseTO(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}
	
}
