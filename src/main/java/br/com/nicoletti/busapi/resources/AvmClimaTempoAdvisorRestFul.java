package br.com.nicoletti.busapi.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseTO;
import br.com.nicoletti.busapi.service.api.avm.AvmFacadeService;

@RestController
@RequestMapping("/rest/avm")
public class AvmClimaTempoAdvisorRestFul {

	@Autowired
	private AvmFacadeService facadeService;

	@RequestMapping(value = "/findCity", method = RequestMethod.POST)
	public ResponseEntity<AvmResponseTO> findCity(@RequestBody Map<String, Object> request) {
		AvmResponseTO out = facadeService.findCity(request);
		return ResponseEntity.ok().body(out);
	}

	@RequestMapping(value = "/currentWeather", method = RequestMethod.POST)
	public ResponseEntity<AvmResponseTO> currentWheater(@RequestBody Map<String, Object> request) {
		AvmResponseTO out = facadeService.currentWheater(request);
		return ResponseEntity.ok().body(out);
	}
}
