package com.desafio.proposta.card.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.desafio.proposta.card.analysis.response.AccountInstallments;
import com.desafio.proposta.card.analysis.response.AccountLocks;
import com.desafio.proposta.card.analysis.response.AccountRenegociation;
import com.desafio.proposta.card.analysis.response.AccountWallets;
import com.desafio.proposta.card.analysis.response.AccountWarnings;
import com.desafio.proposta.card.analysis.response.Expiration;

@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private Long id;
	
	@Column(name = "card_number", updatable = false, nullable = false)
	private String cardNumber;
	
	@Column(name = "issuded_on", nullable = false)
	@NotNull
	private LocalDateTime issuedOn;
	
	@NotBlank
	@Column(nullable = false)
	private String holder;
	
	@Column(name = "account_locks")
	@Embedded
	private List<AccountLocks> accountlocks;
	
	@Column(name = "account_warnings")
	@Embedded
	private List<AccountWarnings> accountWarnings;
	
	@Column(name = "account_wallets")
	@Embedded
	private List<AccountWallets> accountWallets;

	@Column(name = "account_installments")
	@Embedded
	private List<AccountInstallments> accountInstallments;
	
	@Column(name = "account_limit", nullable = false)
	@Positive
	private BigDecimal accountlimit;
	
	@Column(name = "account_renegociation")
	@Embedded
	private AccountRenegociation accountRenegociation;
	
	@NotNull
	@Column(name = "account_expiration", nullable = false)
	@Embedded
	private Expiration accountExpiration;
	
	@NotNull
	@Column(name = "proposal_id", nullable = false)
	private String proposalId;
	
	public Card(@NotBlank String cardNumber,@NotNull LocalDateTime issuedOn,@NotBlank String holder,
			@NotNull List<AccountWarnings> accountWarnings,
			@NotNull List<AccountWallets> accountWallets,
			@NotNull @Positive BigDecimal accountlimit,
			@NotNull Expiration accountExpiration, @NotBlank String proposalId)  {
		this.cardNumber = cardNumber;
		this.issuedOn = issuedOn;
		this.holder = holder;
		this.accountWarnings = accountWarnings;
		this.accountWallets = accountWallets;
		this.accountlimit = accountlimit;
		this.accountExpiration = accountExpiration;
		this.proposalId = proposalId;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
}
