package br.com.nicoletti.busapi.service.impl.avm;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.nicoletti.busapi.beans.mock.to.SleepTO;

@Service
public class BusMockDelayService implements AvmIntegration {

	private static final Logger log = LoggerFactory.getLogger(BusMockDelayService.class);

//	private static final Integer SIXTY_SECONDS = 60000;
	private static final Integer SIXTY_SECONDS = 60000;

	@Override
	public AvmResponseTO execute(AvmRequestTO requestTO) throws SdkExceptions {
		AvmResponseTO out = null;

		Map<String, Object> params = requestTO.getParams();
		Integer delayUnit = getDelay(params);

		SleepTO sleep = sleep(delayUnit);

		if (sleep.getStatus()) {

			AvmResponseTemplate resp = new AvmResponseTemplate.Builder().addResponseText("Mock de action")
					.addResponseText("Delay in seconds: " + TimeUnit.MILLISECONDS.toSeconds(sleep.getDelay()))
					.addResponseText("Delay in millis: " + sleep.getDelay()).addResponseText("Init: " + sleep.getInit())
					.addResponseText("End: " + sleep.getEnd()).build();

			out = new AvmResponseSuccessTO.Builder().addDefaults(requestTO).addType(AvmType.SIMPLE_MESSAGE)
					.addResponse(AvmResponseSuccess.STATUS, Boolean.TRUE).addResponseTextTemplate(resp).build();

		} else {

			out = new AvmResponseErrorTO.Builder().addRequestId(requestTO.getRequestId())
					.addMessage("Delay did not work").build();
		}

		return out;
	}

	private Integer getDelay(Map<String, Object> params) {

		Integer delayUnit = SIXTY_SECONDS;

		try {

			if (params.containsKey("delay")) {
				if (params.get("delay") instanceof Integer) {
					delayUnit = (Integer) params.get("delay");
				} else if (params.get("delay") instanceof String) {
					String delay = (String) params.get("delay");
					delayUnit = Integer.valueOf(delay);
					System.err.println("delayUnit = " + delayUnit);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return delayUnit;
	}

	static public SleepTO sleep(Integer time) {

		SleepTO sleepTO = new SleepTO();

		Boolean status = false;
		Date init = null;
		Date end = null;

		try {

			log.info("init wait");

			init = new Date();
			TimeUnit.MILLISECONDS.sleep(time);
			end = new Date();
			status = true;

			log.info("end wait");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sleepTO.setDelay(time);
		sleepTO.setInit(init);
		sleepTO.setEnd(end);
		sleepTO.setStatus(status);

		return sleepTO;
	}

}
