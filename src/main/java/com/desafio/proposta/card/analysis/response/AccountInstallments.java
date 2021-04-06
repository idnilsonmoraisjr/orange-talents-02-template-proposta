package com.desafio.proposta.card.analysis.response;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class AccountInstallments {

	@JsonProperty("id")
	private String accountInstallmentsId;
	@JsonProperty("quantidade")
	private int accountIstallmentsAmount;
	@JsonProperty("valor")
	private BigDecimal value;
	
	public String getId() {
		return accountInstallmentsId;
	}
	public int getAmount() {
		return accountIstallmentsAmount;
	}
	public BigDecimal getValue() {
		return value;
	}
	
	
}
