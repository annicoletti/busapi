package br.com.nicoletti.busapi;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainCasts {

	public static void main(String[] args) {

		String number = "1000";
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("int", number);
		
		Integer integer = (Integer) map.get("int");
	
	
	}

}
