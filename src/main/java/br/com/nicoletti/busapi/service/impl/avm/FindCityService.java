package br.com.nicoletti.busapi.service.impl.avm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cpqd.avm.sdk.v1.exception.SdkExceptions;
import br.com.cpqd.avm.sdk.v2.enums.AvmResponseSuccess;
import br.com.cpqd.avm.sdk.v2.model.to.AvmRequestTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseErrorTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseSuccessTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseTO;
import br.com.cpqd.avm.sdk.v2.model.to.datamodel.AvmContentTO;
import br.com.cpqd.avm.sdk.v2.model.to.datamodel.AvmItensMenuTO;
import br.com.cpqd.avm.sdk.v2.model.to.datamodel.AvmMenuTO;
import br.com.cpqd.avm.sdk.v2.service.api.AvmIntegration;
import br.com.nicoletti.busapi.beans.forecast.to.CitiesTO;
import br.com.nicoletti.busapi.beans.forecast.to.CityTO;
import br.com.nicoletti.busapi.beans.to.ResponseFailedTO;
import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.service.api.ClimaTempoAdvisorService;

@Service
public class FindCityService implements AvmIntegration {

	@Autowired
	private ClimaTempoAdvisorService climaTempo;

	@Override
	@SuppressWarnings("unchecked")
	public AvmResponseTO execute(AvmRequestTO requestTO) throws SdkExceptions {

		AvmResponseTO out = null;

		Map<String, Object> params = requestTO.getParams();
		String name = null;
		String state = null;
		String country = null;

		if (params.containsKey("name")) {
			name = (String) params.get("name");
		}

		if (params.containsKey("state")) {
			state = (String) params.get("state");
		}

		if (params.containsKey("country")) {
			country = (String) params.get("country");
		}

		CityTO cityTO = new CityTO(null, name, state, country);
		ResponseTO findCities = climaTempo.findCities(cityTO);

		if (findCities.isStatus()) {
			ResponseSuccessTO<CitiesTO> to = (ResponseSuccessTO<CitiesTO>) findCities;
			List<CityTO> cities = to.getData().getCities();
			AvmMenuTO menu = new AvmMenuTO.Builder().build();
			
			int i = 0;
			for (CityTO city : cities) {
				
				AvmItensMenuTO avmItensMenuTO = new AvmItensMenuTO.Builder()
						.addTitle("Cidade: " + city.getName())
						.addInfo("Serviço de previsão de tempo com SDK para o AVM")
						.addContent(new AvmContentTO.Builder()
								.addMatch(String.format("%c", 'A' + (i++)))
								.addText("cidade: " + city.getName() + " / " + city.getState() + " - " + city.getCountry())
								.addValue(String.valueOf(city.getId()))
								.build())
						.build();
				
				menu.addItensMenu(avmItensMenuTO);
			}

			out = new AvmResponseSuccessTO.Builder()
					.addDefaults(requestTO)
					.addDataModel(menu)
					.addResponse(AvmResponseSuccess.STATUS, Boolean.TRUE)
					.build();

		} else {
			ResponseFailedTO to = (ResponseFailedTO) findCities;
			out = new AvmResponseErrorTO.Builder()
					.addRequestId(requestTO.getRequestId())
					.addCode(to.getErrorCode())
					.addMessage(to.getErrorMessage())
					.build();
			
		}

		return out;
	}

	

}
