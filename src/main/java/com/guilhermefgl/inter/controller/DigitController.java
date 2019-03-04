package com.guilhermefgl.inter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermefgl.inter.controller.dto.DigitDto;
import com.guilhermefgl.inter.model.Digit;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.service.DigitService;
import com.guilhermefgl.inter.service.UserService;
import com.guilhermefgl.inter.util.mapper.DigitMapper;

@RestController
@RequestMapping("/api/digitoUnico")
public class DigitController {

	@Autowired
	private DigitService digitService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<DigitDto>> list(@RequestHeader("user") Long userId) {
		List<Digit> digits = new ArrayList<>();
		if (userId != null) {
			Optional<User> userOtp = userService.find(userId);
			if (userOtp.isPresent()) {
				digits = digitService.listByUser(userOtp.get());
			}
		}

		List<DigitDto> result = digits.stream().map(DigitMapper::toDto).collect(Collectors.toList());

		return ResponseEntity.status(result.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(result);
	}

	@PostMapping
	public ResponseEntity<Integer> create(@Valid @RequestBody DigitDto digitDto, @RequestHeader("user") Long userId) {
		User user = null;
		if (userId != null) {
			Optional<User> userOpt = userService.find(userId);
			if (!userOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
			}
			user = userOpt.get();
		}

		Integer result = digitDto.getK() == null ? digitService.uniqueDigit(digitDto.getN())
				: digitService.uniqueDigit(digitDto.getK(), digitDto.getN());

		Digit digit = DigitMapper.toModel(digitDto);
		digit.setDigit(result);
		digit.setUser(user);
		digitService.save(digit);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
