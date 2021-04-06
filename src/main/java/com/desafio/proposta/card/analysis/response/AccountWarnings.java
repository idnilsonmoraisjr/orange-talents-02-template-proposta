package com.desafio.proposta.card.analysis.response;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class AccountWarnings {

	@JsonProperty("validoAte")
	private LocalDateTime validUntil;
	@JsonProperty("destino")
	private String destination;
	
	public LocalDateTime getValidUntil() {
		return validUntil;
	}
	public String getDestination() {
		return destination;
	}
	
	
}
