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
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.service.UserService;
import com.guilhermefgl.inter.util.mapper.UserMapper;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDto>> list() {
		List<UserDto> users = service.list().stream().map(UserMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.status(users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(users);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> find(@PathVariable("id") Long id) {
		Optional<User> user = service.find(id);

		if (user.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(user.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping
	public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
		User createdUser = service.save(UserMapper.toModel(userDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createdUser));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
		Optional<User> user = service.find(id);

		if (user.isPresent()) {
			userDto.setId(id);
			User updatedUser = service.save(UserMapper.toModel(userDto));
			return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(updatedUser));
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDto> delete(@PathVariable("id") Long id) {
		Optional<User> user = service.find(id);

		if (user.isPresent()) {
			service.delete(user.get());
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

}
