package br.com.nicoletti.busapi.service.api.avm;

import java.util.Map;

import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseTO;

public interface AvmFacadeService {

	AvmResponseTO findCity(Map<String, Object> request);

	AvmResponseTO currentWheater(Map<String, Object> request);

}
