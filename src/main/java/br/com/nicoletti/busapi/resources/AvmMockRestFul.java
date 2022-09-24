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
@RequestMapping("/rest/avm/mock")
public class AvmMockRestFul {

	@Autowired
	private AvmFacadeService facadeService;

	@RequestMapping(value = "/action/delay", method = RequestMethod.POST)
	public ResponseEntity<AvmResponseTO> delay(@RequestBody Map<String, Object> request) {
		AvmResponseTO out = facadeService.delay(request);
		return ResponseEntity.ok().body(out);
	}

}
