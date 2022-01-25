package br.com.nicoletti.busapi.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicoletti.busapi.beans.lottery.to.RaffleTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.service.api.LoteriasService;

@RestController
@RequestMapping("/rest/loterias")
public class LoteriasRestFul {

	@Autowired
	private LoteriasService loterias;

	@PostMapping(value = "/getSingleResultOnline")
	public ResponseEntity<ResponseTO> getSingleResultOnline(@RequestBody RaffleTO sorteioTO) {
		ResponseTO out = loterias.getSingleResultOnline(sorteioTO);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/getSingleResult")
	public ResponseEntity<ResponseTO> getSingleResult(@RequestBody RaffleTO sorteioTO) {
		ResponseTO out = loterias.getSingleResultCached(sorteioTO);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/mostRepeatedNumbers")
	public ResponseEntity<ResponseTO> mostRepeatedNumbers(@RequestBody Map<String, Object> params) {
		ResponseTO out = loterias.getMostRepeatedNumbers(params);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/generateRaffleNumbersBaseOnHistory")
	public ResponseEntity<ResponseTO> generateRaffleNumbers(@RequestBody Map<String, Object> params) {
		ResponseTO out = loterias.generateRaffleNumbers(params);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/checkResult")
	public ResponseEntity<ResponseTO> checkResult(@RequestBody Map<String, Object> params) {
		ResponseTO out = loterias.checkResult(params);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/checkResult/resume")
	public ResponseEntity<ResponseTO> checkResultResume(@RequestBody Map<String, Object> params) {
		ResponseTO out = loterias.checkResultResume(params);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/generateRandomNumbers")
	public ResponseEntity<ResponseTO> generateRandomNumbers(@RequestBody Map<String, Object> params) {
		ResponseTO out = loterias.generateRandomNumbers(params);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping(value = "/doBet")
	public ResponseEntity<ResponseTO> doBet(@RequestBody Map<String, Object> params) {
		ResponseTO out = loterias.doBet(params);
		return ResponseEntity.ok().body(out);
	}

}
