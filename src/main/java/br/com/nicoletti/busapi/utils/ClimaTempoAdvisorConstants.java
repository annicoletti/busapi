package br.com.nicoletti.busapi.utils;

public interface ClimaTempoAdvisorConstants {

	String TOKEN = "API_ADVISOR_TOKEN";

	String SERVER = "http://apiadvisor.climatempo.com.br";

	String PATH_SEARCH_CITY_BY = "/api/v1/locale/city";
	String PATH_SEARCH_CURRENT_WEATHER = "/api/v1/weather/locale/%s/current";

	interface FindCity {

		String ID = "id";
		String NAME = "name";
		String STATE = "state";
		String COUNTRY = "country";

	}

	interface CurrentWeather {

		String DATA = "data";

		String CONDITION = "condition";
		String ICON = "icon";
		String SENSATION = "sensation";
		String HUMIDITY = "humidity";
		String TEMPERATURE = "temperature";
		String PRESSURE = "pressure";
		String DATE = "date";
	}

}
