package com.desafio.proposta.proposal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.proposta.proposal.model.Proposal;
import com.desafio.proposta.proposal.repository.ProposalRepository;
import com.desafio.proposta.proposal.request.NewProposalRequest;
import com.desafio.proposta.utils.request.AddressRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProposalsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Mock
	private ProposalRepository proposalRepository;

	private NewProposalRequest proposalRequest;

	private AddressRequest addressRequest;

	private NewProposalRequest invalidProposalRequest;

	private AddressRequest invalidAddressRequest;

	@BeforeEach
	public void setUp() {
		this.addressRequest = new AddressRequest("Street of tests", "1", "Tests neighborhood", "Test City", "16310-970",
				"SP");
		this.proposalRequest = new NewProposalRequest("106.493.570-27", "test@email.com", "User test", addressRequest,
				BigDecimal.TEN);
		this.invalidAddressRequest = new AddressRequest("", "", "", "", "", "");
		this.invalidProposalRequest = new NewProposalRequest("", "", "", invalidAddressRequest, null);
	}

	@Test
	@DisplayName("Should create a proposal succesfully and return 201 ")
	void shouldCreateAProposal_AndReturn201() throws JsonProcessingException, Exception {
		mockMvc.perform(post("/api/proposals")
				.contentType(APPLICATION_JSON)
				.content(toJson(proposalRequest)))
		.andExpect(status()
				.isCreated())
		.andExpect(header()
				.string(LOCATION, "http://localhost/api/proposals/1"));

		Optional<Proposal> proposal = Optional.of(proposalRequest.toProposalEntity());
		when(proposalRepository.findById(1L)).thenReturn(proposal);
		assertEquals("106.493.570-27", proposal.get().getDocument());
	}

	@Test
	@DisplayName("Should fail at create a proposal and return 400")
	void shouldFailCreateProposal_WhenFieldsAreIncorrect() throws JsonProcessingException, Exception {
		mockMvc.perform(post("/api/proposals")
				.contentType(APPLICATION_JSON)
				.content(toJson(invalidProposalRequest)))
				.andExpect(status().isBadRequest());

		Optional<Proposal> proposal = Optional.empty();
		assertThrows(NoSuchElementException.class, () -> {
			when(proposalRepository.save(invalidProposalRequest.toProposalEntity())).thenReturn(proposal.get());
		});
	}

	@Test
	@DisplayName("Should fail at create a proposal and return 422")
	void shouldFailCreateProposal_WhenProposalAlreadyExistsByDocument() throws JsonProcessingException, Exception {

		mockMvc.perform(post("/api/proposals")
				.contentType(APPLICATION_JSON)
				.content(toJson(proposalRequest)))
		.andExpect(status().isCreated());

		mockMvc.perform(post("/api/proposals")
				.contentType(APPLICATION_JSON)
				.content(toJson(proposalRequest)))
				.andExpect(status().isUnprocessableEntity());
	}

	private String toJson(NewProposalRequest proposalRequest) throws JsonProcessingException {
		return mapper.writeValueAsString(proposalRequest);
	}

}

