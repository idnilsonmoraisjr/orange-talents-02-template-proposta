package com.desafio.proposta.proposal.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.utils.CpfOrCnpj;
import com.desafio.proposta.utils.request.AddressRequest;

public class NewProposalRequest {

	@NotBlank
	@CpfOrCnpj
	private String document;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String name;
	@NotNull
	@Valid
	private AddressRequest address;
	@NotNull
	@Positive
	private BigDecimal salary;
	
	@Deprecated
	public NewProposalRequest() {};
	
	public NewProposalRequest(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name,
			@NotNull @Valid AddressRequest address, @NotNull @Positive BigDecimal salary) {
		this.document = document;
		this.email = email;
		this.name = name;
		this.address = address;
		this.salary = salary;
	}

	public String getDocument() {
		return document;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public AddressRequest getAddress() {
		return address;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public Proposal toProposalEntity() {
		return new Proposal(this.document, this.email, this.name, this.address.toAddressEntity(), this.salary);
	}
}
