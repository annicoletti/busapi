package br.com.nicoletti.busapi.service.api;

import br.com.nicoletti.busapi.beans.forecast.to.CityTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;

public interface ClimaTempoAdvisorService {

	ResponseTO findCities(CityTO cityTO);

	ResponseTO currentweather(CityTO cityTO);

}
