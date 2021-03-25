package com.desafio.proposta.proposal.analysis.response;

import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposalAnalysisResponse {

	@JsonProperty("documento")
	private String document;
	@JsonProperty("nome")
	private String name;
	@JsonProperty("resultadoSolicitacao")
	private String analysisResult;
	@JsonProperty("idProposta")
	private String proposalId;

	public String getDocument() {
		return document;
	}

	public String getName() {
		return name;
	}

	public String getAnalysisResult() {
		return analysisResult;
	}

	public String getProposalId() {
		return proposalId;
	}
	
	public ProposalStatus verifyStatus () {
			if("COM_RESTRICAO".equals(analysisResult)) {
				return ProposalStatus.NOT_ELIGIBLE;
			}
			return ProposalStatus.ELIGIBLE;
		}
}
