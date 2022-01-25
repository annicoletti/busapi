package br.com.nicoletti.busapi.service.impl;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.nicoletti.busapi.exception.BusException;
import br.com.nicoletti.busapi.service.api.RestService;
import br.com.nicoletti.busapi.utils.BusExceptions;

@Service
public class RestServicesBean implements RestService {

	public static HttpHeaders defaulHeadersToLotteries() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.COOKIE, "security=true");
		return httpHeaders;
	}

	@Override
	public String doSimpleGet(String url, HttpHeaders httpHeaders) throws BusException {

		try {

			HttpEntity<HttpHeaders> header = new HttpEntity<>(httpHeaders);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, header, String.class);

			HttpStatus statusCode = exchange.getStatusCode();
			if (!HttpStatus.OK.equals(statusCode)) {
				throw new BusException(BusExceptions.REST_SERVICE_RESPONSE_CODE_IS_NOT_OK,
						new String[] { String.valueOf(statusCode) });
			}

			return exchange.getBody();

		} catch (RestClientException e) {
			throw new BusException(BusExceptions.REST_SERVICE_ERROR_TEMPLATE_CLIENT, new String[] { e.getMessage() });
		} catch (Exception e) {
			throw new BusException(BusExceptions.REST_SERVICE_UNKNOW_ERROR, new String[] { e.getMessage() });
		}

	}

	@Override
	public String doSimpleGet(String url) throws BusException {
		return this.doSimpleGet(url, new HttpHeaders());
	}

	@Override
	public String doSimpleGetToLotteries(String url) throws BusException {
		return this.doSimpleGet(url, defaulHeadersToLotteries());
	}

	@Override
	public String doGet(String server, String path, Map<String, Object> parameters, HttpHeaders httpHeaders)
			throws BusException {

		try {

			httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
			HttpEntity<HttpHeaders> header = new HttpEntity<>(httpHeaders);

			String url = urlWithQueryParams(server, path, parameters);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, header, String.class);

			HttpStatus statusCode = exchange.getStatusCode();
			if (!HttpStatus.OK.equals(statusCode)) {
				throw new BusException(BusExceptions.REST_SERVICE_RESPONSE_CODE_IS_NOT_OK,
						new String[] { String.valueOf(statusCode) });
			}

			return exchange.getBody();

		} catch (RestClientException e) {
			throw new BusException(BusExceptions.REST_SERVICE_ERROR_TEMPLATE_CLIENT, new String[] { e.getMessage() });
		} catch (Exception e) {
			throw new BusException(BusExceptions.REST_SERVICE_UNKNOW_ERROR, new String[] { e.getMessage() });
		}
	}

	@Override
	public String doGet(String server, String path, Map<String, Object> parameters) throws BusException {
		return doGet(server, path, parameters, new HttpHeaders());
	}

	private String urlWithQueryParams(String server, String path, Map<String, Object> parameters) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(server.concat(path));
		parameters.forEach((k, v) -> {
			builder.queryParam(k, v);
		});
		return builder.toUriString();
	}

}
