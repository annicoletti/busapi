package br.com.nicoletti.busapi.beans.to;

public final class ResponseFailedTO extends ResponseTO {

	private String errorMessage;

	private String errorCode;

	public ResponseFailedTO() {
		super(Boolean.FALSE);
	}

	public ResponseFailedTO(String errorMessage, String errorCode) {
		super(Boolean.FALSE);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
