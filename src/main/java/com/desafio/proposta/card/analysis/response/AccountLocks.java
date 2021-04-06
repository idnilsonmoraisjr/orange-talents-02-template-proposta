package com.desafio.proposta.card.analysis.response;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class AccountLocks {

	@JsonProperty("id")
	private String accountLockId;
	@JsonProperty("bloqueadoEm")
	private LocalDateTime lockedIn;
	@JsonProperty("sistemaResponsavel")
	private String responsibleSystem;
	@JsonProperty("ativo")
	private boolean active;
	
	public String getId() {
		return accountLockId;
	}
	public LocalDateTime getLockedIn() {
		return lockedIn;
	}
	public String getResponsibleSystem() {
		return responsibleSystem;
	}
	public boolean isActive() {
		return active;
	}
	
	
}
