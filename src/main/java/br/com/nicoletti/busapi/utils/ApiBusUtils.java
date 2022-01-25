package br.com.nicoletti.busapi.utils;

public class ApiBusUtils {

	/**
	 * Retorna {@link} Booelan.FALSE se o texto informado for vazio ou nulo
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNullOrEmpty(String string) {
		if (string == null) {
			return true;
		}

		if (string.trim().isEmpty()) {
			return true;
		}

		return false;
	}

}
