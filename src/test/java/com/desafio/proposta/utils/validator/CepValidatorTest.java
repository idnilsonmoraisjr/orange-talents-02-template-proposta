package com.desafio.proposta.utils.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CepValidatorTest {

	@DisplayName("Should validate if a cep value is valid")
	@Test
	public void validateCorrectCep() {
		CepValidator validator = new CepValidator();
		Assertions.assertTrue(validator.isValid("40290280", null));
		Assertions.assertTrue(validator.isValid("40290281", null));
		Assertions.assertTrue(validator.isValid("40.290-280", null));
		Assertions.assertTrue(validator.isValid("40290-280", null));
	}
	
	@Test
	@DisplayName("Should validate if a cep value is invalid")
	public void validateIncorrectCep() {
		CepValidator validator = new CepValidator();
		Assertions.assertFalse(validator.isValid("4029028", null));
		Assertions.assertFalse(validator.isValid("4029028029", null));
		Assertions.assertFalse(validator.isValid("40.290280", null));
		Assertions.assertFalse(validator.isValid("40aa290-280", null));
		Assertions.assertFalse(validator.isValid("aa290280", null));
		Assertions.assertFalse(validator.isValid("40?290-280", null));
	}
	
	@Test
	@DisplayName("Should validate if a cep value is not presents")
	public void validateNullOrBlankCep() {
		CepValidator validator = new CepValidator();
		Assertions.assertTrue(validator.isValid(null, null));
		Assertions.assertTrue(validator.isValid("", null));
	}
}