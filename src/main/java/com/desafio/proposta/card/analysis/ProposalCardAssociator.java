package com.desafio.proposta.card.analysis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.proposal.repository.ProposalRepository;

@Component
@Transactional
public class ProposalCardAssociator {

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private CardAnalysisSubmitter cardAnalysisSubmitter;
	
	@Scheduled(fixedDelayString = "${frequency.submit-card-analysis}")
	public void verifyProposals() {

		List<Proposal> successfulProposals = proposalRepository
				.findByStatusAndCardNumberIsNull(ProposalStatus.ELIGIBLE);

		if(!successfulProposals.isEmpty()) {
			successfulProposals.stream()
				.forEach(proposal -> associateAProposalCard(proposal));
		}
	}

	private void associateAProposalCard(Proposal proposal) {
			cardAnalysisSubmitter.submit(proposal);
	}
}
