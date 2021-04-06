package com.desafio.proposta.card.analysis.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.desafio.proposta.card.model.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardAnalysisResponse {
	
	@JsonProperty("id")
	private String cardNumber;
	@JsonProperty("emitidoEm")
	private LocalDateTime issuedOn;
	@JsonProperty("titular")
	private String holder;
	@JsonProperty("bloqueios")
	private List<AccountLocks> accountlocks;
	@JsonProperty("avisos")
	private List<AccountWarnings> accountWarnings;
	@JsonProperty("carteiras")
	private List<AccountWallets> accountWallets;
	@JsonProperty("parcelas")
	private List<AccountInstallments> accountInstallments;
	@JsonProperty("limite")
	private BigDecimal accountlimit;
	@JsonProperty("renegociacao")
	private AccountRenegociation accountRenegociation;
	@JsonProperty("vencimento")
	private Expiration accountExpiration;
	@JsonProperty("idProposta")
	private String proposalId;
	
	public String getCardNumber() {
		return cardNumber;
	}
	public LocalDateTime getIssuedOn() {
		return issuedOn;
	}
	public String getHolder() {
		return holder;
	}
	public List<AccountLocks> getAccountlocks() {
		return accountlocks;
	}
	public List<AccountWarnings> getAccountWarnings() {
		return accountWarnings;
	}
	public List<AccountWallets> getAccountWallets() {
		return accountWallets;
	}
	public List<AccountInstallments> getAccountInstallments() {
		return accountInstallments;
	}
	public BigDecimal getAccountlimit() {
		return accountlimit;
	}
	public AccountRenegociation getAccountRenegociation() {
		return accountRenegociation;
	}
	public Expiration getAccountExpiration() {
		return accountExpiration;
	}
	public String getProposalId() {
		return proposalId;
	}

	public Card toCardEntity() {
		return new Card(this.cardNumber, this.issuedOn, this.holder, this.accountWarnings,
				this.accountWallets, this.accountlimit,
				this.accountExpiration, this.proposalId);
	}
}
