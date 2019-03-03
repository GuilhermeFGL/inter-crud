package com.guilhermefgl.inter.service;

import java.util.Collections;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class DigitService {

	private static final int DECIMAL = 10;

	public Integer uniqueDigit(String digit) {
		Integer result = sumDigits(digit);
		while (result > DECIMAL) {
			result = sumDigits(result.toString());
		}
		return result;
	}

	public Integer uniqueDigit(Integer k, String gidit) {
		return uniqueDigit(repeatDigits(k, gidit));
	}

	private String repeatDigits(Integer k, String digit) {
		return String.join(Strings.EMPTY, Collections.nCopies(k, digit));
	}

	private Integer sumDigits(String digit) {
		return digit.chars().map(c -> Character.digit(c, DECIMAL)).sum();
	}

}
