package com.desafio.proposta.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentObfuscator {

	private static final String REGEX_CPF_CNPJ = "([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|"
			+ "([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})";

	public static String obfuscate(String document) {

		Matcher matcher = Pattern.compile(REGEX_CPF_CNPJ).matcher(document);
		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			if (matcher.group().length() == 14) {
				matcher.appendReplacement(sb, "******".concat(matcher.group().substring(8, 14)));
			}
			if (matcher.group().length() == 18) {
				matcher.appendReplacement(sb, "********".concat(matcher.group().substring(11, 18)));
			}
		}

		matcher.appendTail(sb);
		return sb.toString();
	}
}
