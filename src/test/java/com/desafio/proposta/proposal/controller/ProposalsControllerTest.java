package com.desafio.proposta.proposal.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.proposta.card.repository.CardRepository;
import com.desafio.proposta.proposal.analysis.ProposalAnalysisClient;
import com.desafio.proposta.proposal.analysis.ProposalAnalysisSubmitter;
import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.proposal.repository.ProposalRepository;
import com.desafio.proposta.proposal.request.NewProposalRequest;
import com.desafio.proposta.utils.request.AddressRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

@WebMvcTest
@ActiveProfiles("test")

class ProposalsControllerTest {

	@Autowired
	ObjectMapper mapper;
			
	@Autowired
	private ProposalsController proposalsController;
	
	@MockBean
	private ProposalRepository proposalRepository;
	
	@MockBean
	private CardRepository cardRepository;
	
	@MockBean
	private ProposalAnalysisSubmitter analysisSubmitter;
	
	@MockBean
	private ProposalAnalysisClient proposalAnalysisClient;
	
	@MockBean
	private Proposal proposal;

	private NewProposalRequest proposalRequestCpf;
	
	private NewProposalRequest proposalRequestCnpj;

	private AddressRequest addressRequest;

	private NewProposalRequest invalidProposalRequest;

	private AddressRequest invalidAddressRequest;
	

	@BeforeEach
	public void setUp() {
		standaloneSetup(this.proposalsController);
		this.addressRequest = new AddressRequest("Street of tests", "1", "Tests neighborhood", "Test City", "16310-970",
				"SP");
		this.proposalRequestCpf = new NewProposalRequest("106.493.570-27", "test@email.com", "User test", addressRequest,
				BigDecimal.TEN);
		this.proposalRequestCnpj = new NewProposalRequest("45.401.645/0001-74", "test@email.com", "User test", addressRequest,
				BigDecimal.TEN);
		this.invalidAddressRequest = new AddressRequest("", "", "", "", "", "");
		this.invalidProposalRequest = new NewProposalRequest("", "", "", invalidAddressRequest, null);
	}

	@Test
	@DisplayName("Should create a proposal CPF succesfully and return 201 ")
	void shouldCreateAProposalCpf_AndReturn201() throws JsonProcessingException, Exception {
		Proposal newProposal = proposalRequestCpf.toProposalEntity();
		when(this.proposalRepository.save(newProposal))
			.thenReturn(newProposal);
		when(this.analysisSubmitter.submitForAnalysis(newProposal)).thenReturn(ProposalStatus.ELIGIBLE);
		proposal.updateStatus(ProposalStatus.NOT_ELIGIBLE);
	
		 given()
			.contentType("application/json")
			.body(proposalRequestCpf)
		.when()
			.post("/api/proposals")
		.then()
			.statusCode(CREATED.value());
		
		verify(proposal, times(1)).updateStatus(ProposalStatus.NOT_ELIGIBLE);
	}
	
	@Test
	@DisplayName("Should create a proposal CNPJ succesfully and return 201 ")
	void shouldCreateAProposalCnpj_AndReturn201() throws JsonProcessingException, Exception {
		Proposal newProposal = proposalRequestCnpj.toProposalEntity();
		when(this.proposalRepository.save(newProposal))
			.thenReturn(newProposal);
		when(this.analysisSubmitter.submitForAnalysis(newProposal)).thenReturn(ProposalStatus.ELIGIBLE);
		proposal.updateStatus(ProposalStatus.NOT_ELIGIBLE);
	
		 given()
			.contentType("application/json")
			.body(proposalRequestCnpj)
		.when()
			.post("/api/proposals")
		.then()
			.statusCode(CREATED.value());
		
		verify(proposal, times(1)).updateStatus(ProposalStatus.NOT_ELIGIBLE);
	}
	
	@Test
	@DisplayName("Should fail at create a proposal CPF and return 422")
	void shouldFailCreateProposal_WhenProposalAlreadyExistsByDocumentCPF() throws JsonProcessingException, Exception {
		when(this.proposalRepository.existsByDocument(proposalRequestCpf.getDocument())).thenReturn(true);
		
		 given()
				.contentType("application/json")
				.body(proposalRequestCpf)
			.when()
				.post("/api/proposals")
			.then()
				.statusCode(UNPROCESSABLE_ENTITY.value());
		
		verify(proposalRepository, times(0)).save(proposalRequestCpf.toProposalEntity());
		verify(proposal, times(0)).updateStatus(ProposalStatus.NOT_ELIGIBLE);
	}
	
	@Test
	@DisplayName("Should fail at create a proposal CNPJ and return 422")
	void shouldFailCreateProposal_WhenProposalAlreadyExistsByDocumentCnpj() throws JsonProcessingException, Exception {
		when(this.proposalRepository.existsByDocument(proposalRequestCnpj.getDocument())).thenReturn(true);
		
		 given()
				.contentType("application/json")
				.body(proposalRequestCnpj)
			.when()
				.post("/api/proposals")
			.then()
				.statusCode(UNPROCESSABLE_ENTITY.value());
		
		verify(proposalRepository, times(0)).save(proposalRequestCnpj.toProposalEntity());
		verify(proposal, times(0)).updateStatus(ProposalStatus.NOT_ELIGIBLE);
	}


	@Test
	@DisplayName("Should fail at create a proposal and return 400")
	void shouldFailCreateProposal_WhenFieldsAreIncorrect() throws JsonProcessingException, Exception {
		given()
			.contentType("application/json")
			.body(invalidProposalRequest)
		.when()
			.post("/api/proposals")
		.then()
			.statusCode(BAD_REQUEST.value());
		 
		 verify(proposalRepository, times(0)).save(invalidProposalRequest.toProposalEntity());
		 verify(proposal, times(0)).updateStatus(ProposalStatus.NOT_ELIGIBLE);
	}
	
	@Test
	@DisplayName("Should fail at create a proposal when is not eligible and return 422")
	void shouldFailCreateProposal_WhenDocumentIsNotEligible() throws JsonProcessingException, Exception {
		NewProposalRequest notEligibleProposalRequest = new NewProposalRequest("313.035.580-41",
				"test@email.com", "User test", this.addressRequest,
				BigDecimal.TEN);
		
		Proposal notEligibleProposal = notEligibleProposalRequest.toProposalEntity();
		when(this.proposalRepository.save(notEligibleProposal))
		.thenReturn(notEligibleProposal);
		when(this.analysisSubmitter.submitForAnalysis(notEligibleProposal)).thenReturn(ProposalStatus.NOT_ELIGIBLE);
		proposal.updateStatus(ProposalStatus.NOT_ELIGIBLE);
		
		given()
			.contentType("application/json")
			.body(notEligibleProposalRequest)
		.when()
			.post("/api/proposals")
		.then()
			.statusCode(CREATED.value());

		ProposalStatus returnedStatus = notEligibleProposal.getStatus();
		verify(proposal, times(1)).updateStatus(ProposalStatus.NOT_ELIGIBLE);
		assertEquals(ProposalStatus.NOT_ELIGIBLE,returnedStatus);
	}
	
	@Test
	@DisplayName("Should fail at create a proposal when unexpected error ocurres and return ResponseStatusException")
	void shouldFailCreateProposal_WhenUnexpectedErrorOcurres() throws JsonProcessingException, Exception {
		Proposal nullProposal = null;
		when(this.analysisSubmitter.submitForAnalysis(nullProposal)).thenThrow(ResponseStatusException.class);
		
		assertThrows(ResponseStatusException.class, () -> {
			analysisSubmitter.submitForAnalysis(nullProposal);
		});
	}
	
	@Test
	@DisplayName("Should successfully find a proposal by id and return 200 Ok")
	void shouldSuccessfullyFindProposal_AndReturn200() {
		Proposal proposal = proposalRequestCnpj.toProposalEntity();
		
		when(this.proposalRepository.findById(1L))
			.thenReturn(Optional.of(proposal));
		
		 given()
		 	.accept(ContentType.JSON)
		.when()
			.get("api/proposals/{id}", 1L)
		.then()
			.statusCode(HttpStatus.OK.value());
		
		 verify(proposalRepository, times(1)).findById(1L);
	}
	
	@Test
	@DisplayName("Should successfully find a proposal by id and return 404 Not Found")
	void shouldFailFindProposal_AndReturn404() {
 		when(this.proposalRepository.findById(1L))
			.thenReturn(Optional.empty());
		
		 given()
		 	.accept(ContentType.JSON)
		.when()
			.get("api/proposals/{id}", 1L)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		
		 verify(proposalRepository, times(1)).findById(1L);
	}
}

