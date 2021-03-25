package com.desafio.proposta.proposal.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.desafio.proposta.proposal.analysis.ProposalAnalysisClient;
import com.desafio.proposta.proposal.analysis.request.ProposalAnalysisRequest;
import com.desafio.proposta.proposal.analysis.response.ProposalAnalysisResponse;
import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.proposal.repository.ProposalRepository;
import com.desafio.proposta.proposal.request.NewProposalRequest;

import feign.FeignException;

@RestController
@RequestMapping("/api/proposals")
public class ProposalsController {

	private final Logger logger = LoggerFactory.getLogger(ProposalsController.class);
	
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ProposalAnalysisClient proposalAnalysisClient;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> createProposal(@RequestBody @Valid NewProposalRequest request) {
		Proposal newProposal = request.toProposalEntity();
	
		if(proposalRepository.existsByDocument(newProposal.getDocument())) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		proposalRepository.save(newProposal);
		ProposalStatus status = submitForAnalysis(newProposal);
		
		newProposal.updateStatus(status);
		logger.info("A new proposal has been successfully created for the document {} and salary {}",
				newProposal.getDocument(), newProposal.getSalary());
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newProposal.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}

	private ProposalStatus submitForAnalysis(Proposal newProposal) {
		try {
			ProposalAnalysisRequest analysisRequest = new ProposalAnalysisRequest(newProposal);
			ProposalAnalysisResponse analysisResponse = proposalAnalysisClient.submitForAnalysis(analysisRequest);
			return analysisResponse.verifyStatus();
		} catch (FeignException.UnprocessableEntity e ) {
			return ProposalStatus.NOT_ELIGIBLE;
		} catch (Exception e) {
			logger.error("An error occurred while trying to create a new proposal for the document document {} and salary {}",
					newProposal.getDocument(), newProposal.getSalary());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred!");
		}
		
	}
}
