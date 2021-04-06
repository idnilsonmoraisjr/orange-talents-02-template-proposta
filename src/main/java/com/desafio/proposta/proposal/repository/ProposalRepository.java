package com.desafio.proposta.proposal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.desafio.proposta.proposal.enums.ProposalStatus;
import com.desafio.proposta.proposal.model.Proposal;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long>{

	boolean existsByDocument(String document);

	List<Proposal> findByStatusAndCardNumberIsNull(ProposalStatus status);
}
