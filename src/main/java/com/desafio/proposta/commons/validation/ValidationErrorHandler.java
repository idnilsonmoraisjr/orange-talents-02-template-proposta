package com.desafio.proposta.commons.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;
	
	@InitBinder
    private void initDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ValidationErrorOutputDTO handleValidationError(MethodArgumentNotValidException exception) {
		
		List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		return buildValidationErrors(globalErrors, fieldErrors);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ValidationErrorOutputDTO handleValidationError(BindException exception) {
		
		List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		return buildValidationErrors(globalErrors, fieldErrors);
	}
	
	
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ValidationErrorOutputDTO handleValidationError(HttpMessageNotReadableException exception) {
	    	
	    	InvalidFormatException invalidFormat = (InvalidFormatException) exception.getCause();
	    	
	    	List<ObjectError> globalErrors = List.of(new ObjectError("", invalidFormat.getValue()+" não é um valor válido"));
	    	List<FieldError> fieldErrors = List.of();
	    	
	    	return buildValidationErrors(globalErrors,
	    			fieldErrors);
	    }
	
	private ValidationErrorOutputDTO buildValidationErrors(List<ObjectError> globalErrors,
			List<FieldError> fieldErrors) {
		
		ValidationErrorOutputDTO validationErrors = new ValidationErrorOutputDTO();
		
		globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(error)));
		
		fieldErrors.forEach(error -> {
			String errorMessage = getErrorMessage(error);
			validationErrors.addFieldError(error.getField(), errorMessage);
		});
		
		return validationErrors;
	}
	
	private String getErrorMessage(ObjectError error) {
		return messageSource.getMessage(error, LocaleContextHolder.getLocale());
	}
}
