package com.desafio.proposta.utils.response;

import com.desafio.proposta.utils.model.Address;

public class AddressResponse {

	private String publicPlace;
	private String number;
	private String neighborhood;
	private String city;
	private String federatedUnit;
	private String zipCode;
	
	public AddressResponse(Address address) {
		this.publicPlace = address.getPublicPlace();
		this.number = address.getNumber();
		this.neighborhood = address.getNeighborhood();
		this.city = address.getCity();
		this.federatedUnit = address.getFederatedUnit();
		this.zipCode = address.getZipCode();
	}

	public String getPublicPlace() {
		return publicPlace;
	}

	public String getNumber() {
		return number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getCity() {
		return city;
	}

	public String getFederatedUnit() {
		return federatedUnit;
	}

	public String getZipCode() {
		return zipCode;
	}
}
