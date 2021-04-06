package com.desafio.proposta.card.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.desafio.proposta.card.model.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, String> {
}
