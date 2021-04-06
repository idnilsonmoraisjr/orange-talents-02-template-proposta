package com.desafio.proposta.card.analysis.response;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class AccountWallets {

	@JsonProperty("id")
	private String accountWalletsId;
	private String email;
	@JsonProperty("associadaEm")
	private LocalDateTime associatedIn;
	@JsonProperty("emissor")
	private String emitter;
	
	public String getId() {
		return accountWalletsId;
	}
	public String getEmail() {
		return email;
	}
	public LocalDateTime getAssociatedIn() {
		return associatedIn;
	}
	public String getEmitter() {
		return emitter;
	}
}
