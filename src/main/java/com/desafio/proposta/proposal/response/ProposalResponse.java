package com.desafio.proposta.proposal.response;

import java.math.BigDecimal;

import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.utils.response.AddressResponse;

public class ProposalResponse {
	
	private Long id;

	private String document;

	private String email;
	
	private String name;
	
	private AddressResponse address;

	private BigDecimal salary;
	
	private ProposalStatus status;

	public ProposalResponse(Proposal proposal) {
		this.id = proposal.getId();
		this.document = proposal.getDocument();
		this.email = proposal.getEmail();
		this.name = proposal.getName();
		this.address = new AddressResponse(proposal.getAddress());
		this.salary = proposal.getSalary();
		this.status = proposal.getStatus();
	}

	public Long getId() {
		return id;
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

	public AddressResponse getAddress() {
		return address;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public ProposalStatus getStatus() {
		return status;
	}
}
