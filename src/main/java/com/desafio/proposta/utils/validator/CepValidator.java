package com.desafio.proposta.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.desafio.proposta.utils.Cep;

public class CepValidator implements ConstraintValidator<Cep, String>{

	@Override
	public void initialize(final Cep constraintAnnotation) {}
	
	@Override
	public boolean isValid(String cep, ConstraintValidatorContext context) {
		boolean result = false;
		if(cep == null || "".equals(cep)) {
			result = true;
		} else {
			Pattern pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");  
			Matcher matcher = pattern.matcher(cep);
			result = matcher.find();
		}
		return result;
	}
}
