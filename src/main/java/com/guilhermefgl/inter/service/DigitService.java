package com.guilhermefgl.inter.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermefgl.inter.model.Digit;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.model.dao.DigitRepository;

@Service
public class DigitService {

	private static final int DECIMAL = 10;

	@Autowired
	private DigitRepository repository;

	public Digit save(Digit digit) {
		return repository.save(digit);
	}

	public List<Digit> listByUser(User user) {
		return repository.findByUser(user);
	}

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
