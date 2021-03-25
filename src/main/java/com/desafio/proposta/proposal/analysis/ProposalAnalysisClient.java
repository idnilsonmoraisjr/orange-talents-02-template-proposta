package com.desafio.proposta.proposal.analysis;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.desafio.proposta.proposal.analysis.request.ProposalAnalysisRequest;
import com.desafio.proposta.proposal.analysis.response.ProposalAnalysisResponse;

@FeignClient(name = "analysis", url = "${analysis.targetUrl}")
public interface ProposalAnalysisClient {

	@PostMapping("/api/solicitacao")
	@Transactional
	ProposalAnalysisResponse submitForAnalysis(@RequestBody @Valid ProposalAnalysisRequest requestAnalysis); 
}
