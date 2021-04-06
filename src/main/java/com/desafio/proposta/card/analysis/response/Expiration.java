package com.desafio.proposta.card.analysis.response;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Expiration {

	@JsonProperty("id")
	private String expirationId;
	@JsonProperty("dia")
	private int expirationDay;
	@JsonProperty("dataDeCriacao")
	private LocalDateTime expirationCreationDate;
	
	public String getId() {
		return expirationId;
	}
	public int getDay() {
		return expirationDay;
	}
	public LocalDateTime getExpirationCreationDate() {
		return expirationCreationDate;
	}
}
