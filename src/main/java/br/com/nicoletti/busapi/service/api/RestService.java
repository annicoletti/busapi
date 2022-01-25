package br.com.nicoletti.busapi.service.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;

import br.com.nicoletti.busapi.exception.BusException;

public interface RestService {

	String doGet(String server, String path, Map<String, Object> parameters) throws BusException;

	String doGet(String server, String path, Map<String, Object> parameters, HttpHeaders httpHeaders)
			throws BusException;

	String doSimpleGet(String url) throws BusException;

	String doSimpleGet(String url, HttpHeaders httpHeaders) throws BusException;

	String doSimpleGetToLotteries(String url) throws BusException;

}
