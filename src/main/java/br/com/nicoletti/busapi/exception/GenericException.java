package br.com.nicoletti.busapi.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public abstract class GenericException extends Exception {

	private String code = null;

	private String message = null;

	public abstract Locale getLocale();

	public abstract String getBundleName();

	public GenericException(String code) {
		super();
		this.code = code;
		this.initialize(null);
	}

	public GenericException(String code, Object... messageParameters) {
		this(code);
		this.initialize(messageParameters.clone());
	}

	private void initialize(Object[] messageParameters) {
		String messageFromBundle = getMessageFromBundle();
		String messageWithParams = formatMessageWithParams(messageFromBundle, messageParameters);
		this.message = prependCodeToMessage(messageWithParams);
	}

	private String formatMessageWithParams(String message, Object... messageParameters) {
		String formatedMessage = message;
		if (messageParameters != null) {
			formatedMessage = MessageFormat.format(message, messageParameters);
		}
		return formatedMessage;
	}

	private String getMessageFromBundle() {
		ResourceBundle bundle = ResourceBundle.getBundle(getCustomBundleName(), getCustomLocale());
		return bundle.getString(this.getCode());
	}

	private String prependCodeToMessage(String message) {
		StringBuilder messageWithCode = new StringBuilder();
		messageWithCode.append("[");
		messageWithCode.append(this.code);
		messageWithCode.append("] ");
		messageWithCode.append(message);
		return messageWithCode.toString();
	}

	public final String getCode() {
		return code;
	}

	public final String getMessage() {
		return message;
	}

	private String getCustomBundleName() {
		String bundleName = getBundleName();
		return bundleName == null ? "exceptions" : bundleName;
	}

	private Locale getCustomLocale() {
		Locale locale = getLocale();
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}

}