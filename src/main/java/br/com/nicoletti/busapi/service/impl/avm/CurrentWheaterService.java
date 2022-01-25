package br.com.nicoletti.busapi.service.impl.avm;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cpqd.avm.sdk.v1.exception.SdkExceptions;
import br.com.cpqd.avm.sdk.v2.enums.AvmResponseSuccess;
import br.com.cpqd.avm.sdk.v2.enums.AvmType;
import br.com.cpqd.avm.sdk.v2.model.to.AvmRequestTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseErrorTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseSuccessTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseTO;
import br.com.cpqd.avm.sdk.v2.model.to.AvmResponseTemplate;
import br.com.cpqd.avm.sdk.v2.service.api.AvmIntegration;
import br.com.nicoletti.busapi.beans.forecast.to.CityTO;
import br.com.nicoletti.busapi.beans.forecast.to.WeatherTO;
import br.com.nicoletti.busapi.beans.to.ResponseFailedTO;
import br.com.nicoletti.busapi.beans.to.ResponseSuccessTO;
import br.com.nicoletti.busapi.beans.to.ResponseTO;
import br.com.nicoletti.busapi.service.api.ClimaTempoAdvisorService;

@Service
public class CurrentWheaterService implements AvmIntegration {

	@Autowired
	private ClimaTempoAdvisorService climaTempo;

	@Override
	@SuppressWarnings("unchecked")
	public AvmResponseTO execute(AvmRequestTO requestTO) throws SdkExceptions {
		AvmResponseTO out = null;

		Map<String, Object> params = requestTO.getParams();
		Integer id = null;

		if (params.containsKey("id")) {
			Object idObject = params.get("id");
			id = idObject instanceof Integer ? (Integer) params.get("id") : Integer.valueOf((String) params.get("id"));
		}

		CityTO cityTO = new CityTO(id);
		ResponseTO forecast = climaTempo.currentweather(cityTO);

		if (forecast.isStatus()) {
			ResponseSuccessTO<WeatherTO> to = (ResponseSuccessTO<WeatherTO>) forecast;
			JSONObject jsonObject = new JSONObject(to.getData());
			
			AvmResponseTemplate resp = new AvmResponseTemplate.Builder()
					.addResponseText("A previsão to tempo de hoje para a cidade " + to.getData().getCity().getName() + " é")
					.addResponseText("temperatura: " + to.getData().getTemperature() + " ºC")
					.addResponseText("humidade: " + to.getData().getHumidity())
					.addResponseText("sensação: " + to.getData().getSensation())
					.addResponseText("condição: " + to.getData().getCondition())
					.addResponseText("pressão: " + to.getData().getPressure())
					.build();
			
			out = new AvmResponseSuccessTO.Builder()
					.addDefaults(requestTO)
					.addType(AvmType.SIMPLE_MESSAGE)
					.addResponse(AvmResponseSuccess.STATUS, Boolean.TRUE)
					.addResponse("res_json", jsonObject.toString())
					.addResponse("res_map", jsonObject.toMap())
					.addResponse("weather", to)
					.addResponseTextTemplate(resp)
					.build();
			
		} else {
			ResponseFailedTO to = (ResponseFailedTO) forecast;
			
			out = new AvmResponseErrorTO.Builder()
					.addRequestId(requestTO.getRequestId())
					.addMessage(to.getErrorMessage())
					.addCode(to.getErrorCode())
					.build();
			
		}

		
		return out;
	}

}
