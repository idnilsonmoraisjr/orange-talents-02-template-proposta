package com.desafio.proposta.card.analysis;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.desafio.proposta.card.analysis.request.CardAnalysisRequest;
import com.desafio.proposta.card.analysis.response.CardAnalysisResponse;

@FeignClient(name = "card-analysis", url = "${card-analysis.targetUrl}")
public interface CardAnalysisClient {

	@PostMapping("/api/cartoes")
	@Transactional
	CardAnalysisResponse submitForAnalysis(@RequestBody @Valid CardAnalysisRequest requestAnalysis);
}
