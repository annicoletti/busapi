package br.com.nicoletti.busapi.resources;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;

@RestController
@RequestMapping("/health/check")
public class HealthRestFul {

	@GetMapping(value = "/echo")
	public ResponseEntity<ResponseTO> echo() {
		ResponseTO out = new ResponseSuccessTO<>(new Date());
		return ResponseEntity.ok().body(out);
	}

}
