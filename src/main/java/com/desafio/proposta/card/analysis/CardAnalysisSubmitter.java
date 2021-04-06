package com.desafio.proposta.card.analysis;

import static com.desafio.proposta.utils.DocumentObfuscator.obfuscate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.proposta.card.analysis.request.CardAnalysisRequest;
import com.desafio.proposta.card.analysis.response.CardAnalysisResponse;
import com.desafio.proposta.card.model.Card;
import com.desafio.proposta.card.repository.CardRepository;
import com.desafio.proposta.proposal.model.Proposal;

@Service
@Transactional
public class CardAnalysisSubmitter {

	private final Logger logger = LoggerFactory.getLogger(CardAnalysisSubmitter.class);
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CardAnalysisClient cardAnalysisClient;
	
	public void submit(Proposal proposal) {
		
		if(proposal.getCardNumber() != null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "There is already a card associated with this proposal!");
		}
		try {
		CardAnalysisRequest cardAnalysisRequest = new CardAnalysisRequest(proposal);
		CardAnalysisResponse cardAnalysisResponse = cardAnalysisClient.submitForAnalysis(cardAnalysisRequest);

		Card proposalCard = cardAnalysisResponse.toCardEntity();
		cardRepository.save(proposalCard);

		proposal.associateCardNumber(proposalCard.getCardNumber());
		logger.info("A card has been successfully associated for the document proposal {} ",
				obfuscate(proposal.getDocument()));
		} catch (Exception e) {
			logger.error("An error occurred while trying to associated a card for the document proposal {}",
					obfuscate(proposal.getDocument()));
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred!");
		}
	}
}
