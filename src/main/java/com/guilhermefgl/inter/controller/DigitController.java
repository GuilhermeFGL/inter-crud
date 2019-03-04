package com.guilhermefgl.inter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermefgl.inter.controller.dto.DigitDto;
import com.guilhermefgl.inter.controller.validation.ValidationErrorResponse;
import com.guilhermefgl.inter.model.Digit;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.service.CacheService;
import com.guilhermefgl.inter.service.DigitService;
import com.guilhermefgl.inter.service.UserService;
import com.guilhermefgl.inter.util.mapper.DigitMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/digitoUnico")
public class DigitController {

	@Autowired
	private DigitService digitService;

	@Autowired
	private UserService userService;

	@Autowired
	private CacheService cacheService;

	@ApiOperation(value = "list", notes = "lista dígitos únicos de um usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 204, message = "NO CONTENT", response = List.class) })
	@GetMapping
	public ResponseEntity<List<DigitDto>> list(
			@ApiParam(value = "id do usuário") @Nullable @RequestHeader("user") Long userId) {
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

	@ApiOperation(value = "create", notes = "calcula dígito único opcionalmente referecia ao usuário")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED", response = Integer.class),
			@ApiResponse(code = 403, message = "FORBIDDEN"),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = ValidationErrorResponse.class) })
	@PostMapping
	public ResponseEntity<Integer> create(
			@ApiParam(value = "parâmetros do dígito único") @Valid @RequestBody DigitDto digitDto,
			@ApiParam(value = "usuário associado ao dígito único") @Nullable @RequestHeader("user") Long userId) {
		User user = null;
		if (userId != null) {
			Optional<User> userOpt = userService.find(userId);
			if (!userOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
			}
			user = userOpt.get();
		}

		Integer result = cacheService.get(digitDto);
		if (result == null) {
			result = digitDto.getK() == null ? digitService.uniqueDigit(digitDto.getN())
					: digitService.uniqueDigit(digitDto.getK(), digitDto.getN());
			cacheService.put(digitDto, result);
		}

		Digit digit = DigitMapper.toModel(digitDto);
		digit.setDigit(result);
		digit.setUser(user);
		digitService.save(digit);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

}
