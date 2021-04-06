package com.desafio.proposta.proposal.analysis;

import static com.desafio.proposta.utils.DocumentObfuscator.obfuscate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.proposta.proposal.analysis.request.ProposalAnalysisRequest;
import com.desafio.proposta.proposal.analysis.response.ProposalAnalysisResponse;
import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;

import feign.FeignException;

@Service
public class ProposalAnalysisSubmitter {

	private final Logger logger = LoggerFactory.getLogger(ProposalAnalysisSubmitter.class);
	
	@Autowired
	private ProposalAnalysisClient proposalAnalysisClient;
	
	public ProposalStatus submitForAnalysis(Proposal newProposal) {
		try {
			ProposalAnalysisRequest analysisRequest = new ProposalAnalysisRequest(newProposal);
			ProposalAnalysisResponse analysisResponse = proposalAnalysisClient.submitForAnalysis(analysisRequest);
			return analysisResponse.verifyStatus();
		} catch (FeignException.UnprocessableEntity e ) {
			return ProposalStatus.NOT_ELIGIBLE;
		} catch (Exception e) {
			logger.error("An error occurred while trying to create a new proposal for the document {} and salary {}",
					obfuscate(newProposal.getDocument()), newProposal.getSalary());
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred!");
		}
	}

}
