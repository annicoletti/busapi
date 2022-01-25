package br.com.nicoletti.busapi.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.nicoletti.busapi.beans.forecast.to.CitiesTO;
import br.com.nicoletti.busapi.beans.forecast.to.CityTO;
import br.com.nicoletti.busapi.beans.forecast.to.WeatherTO;
import br.com.nicoletti.busapi.beans.to.ResponseFailedTO;
import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.exception.BusException;
import br.com.nicoletti.busapi.service.api.ClimaTempoAdvisorService;
import br.com.nicoletti.busapi.service.api.RestService;
import br.com.nicoletti.busapi.utils.ApiBusUtils;
import br.com.nicoletti.busapi.utils.BusExceptions;
import br.com.nicoletti.busapi.utils.ClimaTempoAdvisorConstants;

@Service
public class ClimaTempoAdvisorServiceBean implements ClimaTempoAdvisorService {

//	private static String TOKEN = "27b48bd77d9eac00b6c6415f376cb3d8";

	@Autowired
	private RestService restService;

	@Value("${forecastservice.token}")
	private String token;

	@Override
	public ResponseTO findCities(CityTO cityTO) {
		ResponseSuccessTO<CitiesTO> responseSuccessTO = new ResponseSuccessTO<CitiesTO>();
		ResponseFailedTO responseFailedTO = new ResponseFailedTO();
		List<CityTO> listCities = new ArrayList<CityTO>();

		try {

			this.validateCity(cityTO);

			Map<String, Object> parameters = new LinkedHashMap<String, Object>();
			if (cityTO.getName() != null) {
				parameters.put("name", cityTO.getName());
			}

			if (cityTO.getCountry() != null) {
				parameters.put("country", cityTO.getCountry());
			}

			if (cityTO.getState() != null) {
				parameters.put("state", cityTO.getState());
			}

			parameters.put("token", token);

			String response = restService.doGet(ClimaTempoAdvisorConstants.SERVER,
					ClimaTempoAdvisorConstants.PATH_SEARCH_CITY_BY, parameters);

			Object object = new JSONTokener(response).nextValue();
			if (object instanceof JSONObject) {
				JSONObject jsonObject = new JSONObject(response);
				listCities.add(parseJsonToCityTO(jsonObject));

			} else {
				JSONArray jsonArray = new JSONArray(response);
				jsonArray.forEach(item -> {
					JSONObject jsonObject = (JSONObject) item;
					listCities.add(parseJsonToCityTO(jsonObject));
				});

			}

			if (listCities.isEmpty()) {
				throw new BusException(BusExceptions.FORECAST_SERVICE_CITY_NOT_FOUND);
			}

			CitiesTO citiesTO = new CitiesTO();
			citiesTO.setCities(listCities);
			citiesTO.setQuantity(listCities.size());

			responseSuccessTO.setData(citiesTO);
			return responseSuccessTO;

		} catch (BusException e) {
			responseFailedTO.setErrorCode(e.getCode());
			responseFailedTO.setErrorMessage(e.getMessage());
			return responseFailedTO;

		} catch (Exception e) {
			e.printStackTrace();
			responseFailedTO.setErrorCode(BusExceptions.FORECAST_SERVICE_UNKNOW_ERROR);
			responseFailedTO.setErrorMessage(e.getMessage());
			return responseFailedTO;
		}
	}

	private void validateCity(CityTO cityTO) throws BusException {
		if (ApiBusUtils.isNullOrEmpty(cityTO.getName()) && ApiBusUtils.isNullOrEmpty(cityTO.getCountry())
				&& ApiBusUtils.isNullOrEmpty(cityTO.getState())) {
			throw new BusException(BusExceptions.FORECAST_SERVICE_MISSING_PARAMETER_CITY_NAME);
		}

	}

	private CityTO parseJsonToCityTO(JSONObject jsonObject) {
		Integer id = jsonObject.getInt(ClimaTempoAdvisorConstants.FindCity.ID);
		String name = jsonObject.getString(ClimaTempoAdvisorConstants.FindCity.NAME).trim();
		String state = jsonObject.getString(ClimaTempoAdvisorConstants.FindCity.STATE).trim();
		String country = jsonObject.getString(ClimaTempoAdvisorConstants.FindCity.COUNTRY).trim();

		return new CityTO(id, name, state, country);

	}

	@Override
	public ResponseTO currentweather(CityTO cityTO) {
		ResponseSuccessTO<WeatherTO> responseSuccessTO = new ResponseSuccessTO<WeatherTO>();
		ResponseFailedTO responseFailedTO = new ResponseFailedTO();

		try {

			Map<String, Object> parameters = new LinkedHashMap<String, Object>();
			if (cityTO.getId() == null && cityTO.getId() < 0) {
				throw new BusException(BusExceptions.FORECAST_SERVICE_MISSING_PARAMETER_CITY_ID);
			}

			parameters.put("id", cityTO.getId());
			parameters.put("token", token);

			String path = String.format(ClimaTempoAdvisorConstants.PATH_SEARCH_CURRENT_WEATHER, cityTO.getId());
			String response = restService.doGet(ClimaTempoAdvisorConstants.SERVER, path, parameters);

			JSONObject jsonObject = new JSONObject(response);
			WeatherTO weatherTO = parseJsontoWeaterTO(jsonObject);
			responseSuccessTO.setData(weatherTO);

			return responseSuccessTO;

		} catch (BusException e) {
			responseFailedTO.setErrorCode(e.getCode());
			responseFailedTO.setErrorMessage(e.getMessage());
			return responseFailedTO;

		} catch (Exception e) {
			e.printStackTrace();
			responseFailedTO.setErrorCode("CODE-0001");
			responseFailedTO.setErrorMessage(e.getLocalizedMessage());
			return responseFailedTO;

		}
	}

	private WeatherTO parseJsontoWeaterTO(JSONObject jsonObject) {

		CityTO cityTO = parseJsonToCityTO(jsonObject);

		JSONObject jsonData = jsonObject.getJSONObject(ClimaTempoAdvisorConstants.CurrentWeather.DATA);
		WeatherTO weatherTO = new WeatherTO();
		weatherTO.setCondition(jsonData.getString(ClimaTempoAdvisorConstants.CurrentWeather.CONDITION));
		weatherTO.setDate(null);
		weatherTO.setHumidity(jsonData.getDouble(ClimaTempoAdvisorConstants.CurrentWeather.HUMIDITY));
		weatherTO.setIcon(jsonData.getString(ClimaTempoAdvisorConstants.CurrentWeather.ICON));
		weatherTO.setPressure(jsonData.getDouble(ClimaTempoAdvisorConstants.CurrentWeather.PRESSURE));
		weatherTO.setSensation(jsonData.getDouble(ClimaTempoAdvisorConstants.CurrentWeather.SENSATION));
		weatherTO.setTemperature(jsonData.getDouble(ClimaTempoAdvisorConstants.CurrentWeather.TEMPERATURE));
		weatherTO.setCity(cityTO);
		return weatherTO;
	}

}
