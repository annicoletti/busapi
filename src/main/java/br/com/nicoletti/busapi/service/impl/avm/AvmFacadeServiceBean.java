package br.com.nicoletti.busapi.service.impl.avm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseTO;
import br.com.cpqd.avm.sdk.v2.service.api.AvmExecuteFacade;
import br.com.cpqd.avm.sdk.v2.service.api.AvmIntegration;
import br.com.nicoletti.busapi.service.api.avm.AvmFacadeService;

@Service
public class AvmFacadeServiceBean implements AvmFacadeService, AvmExecuteFacade {

	@Autowired
	private AvmIntegration findCityService;

	@Autowired
	private AvmIntegration currentWheaterService;

	@Override
	public AvmResponseTO findCity(Map<String, Object> request) {
		return process(findCityService, request);
	}

	@Override
	public AvmResponseTO currentWheater(Map<String, Object> request) {
		return process(currentWheaterService, request);
	}

}
