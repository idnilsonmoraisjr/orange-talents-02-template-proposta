package com.desafio.proposta.utils.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Address {

	@NotBlank
	@Column(nullable=false)
	private String publicPlace;
	@NotBlank
	@Column(nullable=false)
	private String number;
	@NotBlank
	@Column(nullable=false)
	private String neighborhood;
	@NotBlank 
	@Column(nullable=false)
	private String city;
	@NotBlank
	@Column(nullable=false)
	private String federatedUnit;
	@NotBlank 
	@Column(nullable=false)
	private String zipCode;
	
	@Deprecated
	public Address() {}

	public Address(@NotBlank String publicPlace, @NotBlank String number, @NotBlank String neighborhood,
			@NotBlank String city, @NotBlank String federatedUnit, @NotBlank String zipCode) {
				this.publicPlace = publicPlace;
				this.number = number;
				this.neighborhood = neighborhood;
				this.city = city;
				this.federatedUnit = federatedUnit;
				this.zipCode = zipCode;
	}

}
