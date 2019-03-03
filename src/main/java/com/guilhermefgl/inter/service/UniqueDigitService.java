package com.guilhermefgl.inter.service;

import org.springframework.stereotype.Service;

@Service
public class UniqueDigitService {
	
	private static final int DECIMAL = 10;

	public Integer uniqueDigit(String digit) {
		Integer result = sumDigits(digit);
		while (result > DECIMAL) {
			result = sumDigits(result.toString());
		}
		return result;
	}
	
	public Integer uniqueDigit(Integer k, Integer n) {
		StringBuilder digit = new StringBuilder();
		
		while (k > 0) {
			digit.append(n);
			k--;
		}
		
		return sumDigits(digit.toString());
	}
	
	private Integer sumDigits(String digit) {
		Integer result = 0;
		
		for (char c : digit.toCharArray()) {
			result += Character.digit(c, DECIMAL);
		}
		
		return result;
	}

}
