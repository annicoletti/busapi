package br.com.nicoletti.busapi.exception;

import java.io.Serializable;
import java.util.Locale;

public class BusException extends GenericException implements Serializable {

	private static final long serialVersionUID = 1L;

	public BusException(String code) {
		super(code);
	}

	public BusException(String code, Object[] messageParameters) {
		super(code, messageParameters);
	}

	@Override
	public String getBundleName() {
		return "exceptions_pt_BR";
	}

	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}
}
