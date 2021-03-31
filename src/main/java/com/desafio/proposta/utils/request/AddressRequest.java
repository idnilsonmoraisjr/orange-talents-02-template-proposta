package com.desafio.proposta.utils.request;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.desafio.proposta.utils.Cep;
import com.desafio.proposta.utils.model.Address;

@Validated
public class AddressRequest {

	@NotBlank
	private String publicPlace;
	@NotBlank
	private String number;
	@NotBlank
	private String neighborhood;
	@NotBlank
	private String city;
	@NotBlank
	private String federatedUnit;
	@NotBlank
	@Cep
	private String zipCode;
	
	@Deprecated
	public AddressRequest() {}
	
	public AddressRequest(@NotBlank String publicPlace, @NotBlank String number, @NotBlank String neighborhood,
			@NotBlank String city, @NotBlank String zipCode, @NotBlank String federatedUnit) {
		this.publicPlace = publicPlace;
		this.number = number;
		this.neighborhood = neighborhood;
		this.city = city;
		this.federatedUnit = federatedUnit;
		this.zipCode = zipCode;
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

	public Address toAddressEntity() {
		return new Address(this.publicPlace, this.number, this.neighborhood, this.city,this.federatedUnit, this.zipCode);
	}
}
