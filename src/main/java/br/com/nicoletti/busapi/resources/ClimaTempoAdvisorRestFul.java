package br.com.nicoletti.busapi.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicoletti.busapi.beans.forecast.to.CityTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.service.api.ClimaTempoAdvisorService;

@RestController
@RequestMapping("/rest/forecast")
public class ClimaTempoAdvisorRestFul {

	@Autowired
	private ClimaTempoAdvisorService climaTempoAdvisorService;

	@PostMapping(value = "/findCity")
	public ResponseEntity<ResponseTO> findCity(@RequestBody CityTO cityTO) {
		ResponseTO out = climaTempoAdvisorService.findCities(cityTO);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/currentWeather")
	public ResponseEntity<ResponseTO> currentweather(@RequestBody CityTO cityTO) {
		ResponseTO out = climaTempoAdvisorService.currentweather(cityTO);
		return ResponseEntity.ok().body(out);
	}

}
