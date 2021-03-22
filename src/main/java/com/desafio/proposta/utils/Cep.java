package com.desafio.proposta.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.desafio.proposta.utils.validator.CepValidator;

@Documented
@Target({ FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CepValidator.class)
public @interface Cep {

	Class<?>[] groups() default {};
	
	String message() default "CEP must be valid!";
	
	Class<? extends Payload>[] payload() default{};
}
