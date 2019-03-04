package com.guilhermefgl.inter.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilhermefgl.inter.controller.dto.UserDto;
import com.guilhermefgl.inter.controller.validation.ValidationErrorResponse;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.service.UserService;
import com.guilhermefgl.inter.util.mapper.UserMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "list", notes = "lista usuários cadastrados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 204, message = "NO CONTENT", response = List.class) })
	@GetMapping
	public ResponseEntity<List<UserDto>> list() {
		List<UserDto> users = service.list().stream().map(UserMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.status(users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(users);
	}

	@ApiOperation(value = "find", notes = "recupera usuário cadastrado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UserDto.class),
			@ApiResponse(code = 404, message = "NOT FOUND") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> find(@ApiParam(value = "id do usuário") @PathVariable("id") Long id) {
		Optional<User> user = service.find(id);

		if (user.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(user.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@ApiOperation(value = "create", notes = "cadastra usuário")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED", response = UserDto.class),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = ValidationErrorResponse.class) })
	@PostMapping
	public ResponseEntity<UserDto> create(@ApiParam(value = "corpo do usuário") @Valid @RequestBody UserDto userDto) {
		userDto.setId(null);
		User createdUser = service.save(UserMapper.toModel(userDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createdUser));
	}

	@ApiOperation(value = "update", notes = "atualiza usuário cadastrado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UserDto.class),
			@ApiResponse(code = 204, message = "NO CONTENT"),
			@ApiResponse(code = 400, message = "BAD REQUEST", response = ValidationErrorResponse.class) })
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDto> update(@ApiParam(value = "id do usuário") @PathVariable("id") Long id,
			@ApiParam(value = "corpo do usuário") @Valid @RequestBody UserDto userDto) {
		Optional<User> user = service.find(id);

		if (user.isPresent()) {
			userDto.setId(id);
			User updatedUser = service.save(UserMapper.toModel(userDto));
			return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(updatedUser));
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

	@ApiOperation(value = "delete", notes = "deleta usuário cadastrado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UserDto.class),
			@ApiResponse(code = 204, message = "NO CONTENT") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDto> delete(@ApiParam(value = "id do usuário") @PathVariable("id") Long id) {
		Optional<User> user = service.find(id);

		if (user.isPresent()) {
			service.delete(user.get());
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

}
