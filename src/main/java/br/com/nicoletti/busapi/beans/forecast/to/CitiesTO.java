package br.com.nicoletti.busapi.beans.forecast.to;

import java.util.List;

public class CitiesTO {

	private Integer quantity;

	private List<CityTO> cities;

	public CitiesTO() {
	}

	public CitiesTO(Integer quantity, List<CityTO> cities) {
		this.quantity = quantity;
		this.cities = cities;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<CityTO> getCities() {
		return cities;
	}

	public void setCities(List<CityTO> cities) {
		this.cities = cities;
	}

}
