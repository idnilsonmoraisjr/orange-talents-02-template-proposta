package com.desafio.proposta.proposal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.utils.model.Address;

@Entity
public class Proposal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(nullable = false)
	private String document;
	@NotBlank @Email
	@Column(nullable = false)
	private String email;
	@NotBlank
	@Column(nullable = false)
	private String name;
	@NotNull
	@Embedded
	private Address address;
	@NotNull @Positive
	@Column(nullable = false)
	private BigDecimal salary;
	@Enumerated(EnumType.STRING)
	@NotNull
	private ProposalStatus status = ProposalStatus.NOT_ELIGIBLE;
	@Column(name = "card_number")
	private String cardNumber;
	
	@Deprecated
	public Proposal() {}
	
	public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name,
			@NotNull Address address, @NotNull @Positive BigDecimal salary) {
				this.document = document;
				this.email = email;
				this.name = name;
				this.address = address;
				this.salary = salary;
	}

	public Long getId() {
		return id;
	}

	public String getDocument() {
		return document;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void updateStatus(ProposalStatus status) {
		this.status = status;
	}

	public void associateCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
