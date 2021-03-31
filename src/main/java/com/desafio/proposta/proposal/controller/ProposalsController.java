package com.desafio.proposta.proposal.controller;

import static com.desafio.proposta.utils.DocumentObfuscator.obfuscate;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.desafio.proposta.proposal.analysis.ProposalAnalysisSubmitter;
import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.proposal.repository.ProposalRepository;
import com.desafio.proposta.proposal.request.NewProposalRequest;

@RestController
@RequestMapping("/api/proposals")
public class ProposalsController {

	private final Logger logger = LoggerFactory.getLogger(ProposalsController.class);
	
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ProposalAnalysisSubmitter analysisSubmitter;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> createProposal(@RequestBody @Valid NewProposalRequest request) {
		Proposal newProposal = request.toProposalEntity();
	
		if(proposalRepository.existsByDocument(newProposal.getDocument())) {
			return ResponseEntity.unprocessableEntity().build();
		}
		
		proposalRepository.save(newProposal);
		ProposalStatus status = analysisSubmitter.submitForAnalysis(newProposal);
		
		newProposal.updateStatus(status);
		logger.info("A new proposal has been successfully created for the document {} and salary {}",
				obfuscate(newProposal.getDocument()), newProposal.getSalary());
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newProposal.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
