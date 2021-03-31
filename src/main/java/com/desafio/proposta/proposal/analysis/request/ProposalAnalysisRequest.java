package com.desafio.proposta.proposal.analysis.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.utils.CpfOrCnpj;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposalAnalysisRequest {

	@NotBlank
	@CpfOrCnpj
	@JsonProperty("documento")
	private String document;
	@NotBlank
	@JsonProperty("nome")
	private String name;
	@NotBlank
	@JsonProperty("idProposta")
	private String proposalId;

	public ProposalAnalysisRequest(@Valid @NotNull Proposal newProposal) {
		this.document = newProposal.getDocument();
		this.name = newProposal.getName();
		this.proposalId = newProposal.getId().toString();
	}

	public String getDocument() {
		return document;
	}

	public String getName() {
		return name;
	}

	public String getProposalId() {
		return proposalId;
	}
}
