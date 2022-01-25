package br.com.nicoletti.busapi.beans.to;

public final class ResponseSuccessTO<T> extends ResponseTO {

	private T data;

	public ResponseSuccessTO() {
		super(Boolean.TRUE);
	}

	public ResponseSuccessTO(T data) {
		super(Boolean.TRUE);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
