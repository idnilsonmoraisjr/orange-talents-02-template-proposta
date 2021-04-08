package com.desafio.proposta.card.analysis;

import static com.desafio.proposta.utils.DocumentObfuscator.obfuscate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.proposta.card.model.Card;
import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.proposal.repository.ProposalRepository;

@Component
@Transactional
public class ProposalCardAssociator {

	private final Logger logger = LoggerFactory.getLogger(ProposalCardAssociator.class);

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private CardAnalysisSubmitter cardAnalysisSubmitter;

	@Scheduled(fixedDelayString = "${frequency.submit-card-analysis}")
	public void verifyProposals() {

		List<Proposal> successfulProposals = proposalRepository
				.findByStatusAndCardNumberIsNull(ProposalStatus.ELIGIBLE);

		if (!successfulProposals.isEmpty()) {
			successfulProposals.stream()
				.filter(proposal -> !proposal.haveACard())
				.forEach(proposal -> associateAProposalCard(proposal));
		}
	}

	private void associateAProposalCard(Proposal proposal) {
		if (proposal.haveACard()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"There is already a card associated with this proposal!");
		}

		Card proposalCard = cardAnalysisSubmitter.submit(proposal);
		proposal.associateCardNumber(proposalCard.getCardNumber());
		logger.info("A card has been successfully associated for the document proposal {} ",
				obfuscate(proposal.getDocument()));
	}
}

