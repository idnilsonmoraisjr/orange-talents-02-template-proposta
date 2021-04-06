package com.desafio.proposta.card.analysis.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class AccountRenegociation {

	@JsonProperty("id")
	private String accountRenegociationId;
	@JsonProperty("quantidade")
	private Integer accountRenegociationAmount;
	@JsonProperty("valor")
	private BigDecimal value;
	@JsonProperty("dataDeCriacao")
	private LocalDateTime renegociationCreationDate;
	
	public String getId() {
		return accountRenegociationId;
	}
	public Integer getAmount() {
		return accountRenegociationAmount;
	}
	public BigDecimal getValue() {
		return value;
	}
	public LocalDateTime getCreationDate() {
		return renegociationCreationDate;
	}
}
