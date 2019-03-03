package com.guilhermefgl.inter.util.mapper;

import com.guilhermefgl.inter.controller.dto.UserDto;
import com.guilhermefgl.inter.model.User;

public final class UserMapper {

	private UserMapper() {
	}

	public static UserDto toDto(User model) {
		UserDto dto = new UserDto();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setEmail(model.getEmail());
		return dto;
	}

	public static User toModel(UserDto dto) {
		User model = new User();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setEmail(dto.getEmail());
		return model;
	}

}
