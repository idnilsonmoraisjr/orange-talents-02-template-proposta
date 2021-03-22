package com.desafio.proposta.commons.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorOutputDTO {

	List<String> globalErrorMessages = new ArrayList<>();
	private List<FieldErrorOutputDTO> fieldErrors = new ArrayList<>();
	
	public void addError(String message) {
		globalErrorMessages.add(message);
	}
	
	public void addFieldError(String field, String message) {
		FieldErrorOutputDTO fieldError = new FieldErrorOutputDTO(field, message);
		fieldErrors.add(fieldError);
	}
	
	public List<String> getGlobalErrorsMessages() {
		return globalErrorMessages;
	}
	
	public List<FieldErrorOutputDTO> getErrors() {
		return fieldErrors;
	}
	
	public int getNumberOfErrors() {
		return this.globalErrorMessages.size() + this.fieldErrors.size();
	}
}
