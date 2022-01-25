package br.com.nicoletti.busapi.beans.forecast.to;

public class CityTO {

	private Integer id;

	private String name;

	private String state;

	private String country;

	public CityTO() {
	}

	public CityTO(Integer id) {
		this.id = id;
	}

	public CityTO(String name, String state, String country) {
		this.name = name;
		this.state = state;
		this.country = country;
	}

	public CityTO(Integer id, String name, String state, String country) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.country = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
