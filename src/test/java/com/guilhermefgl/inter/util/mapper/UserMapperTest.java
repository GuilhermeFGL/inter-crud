package com.guilhermefgl.inter.util.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.guilhermefgl.inter.controller.dto.UserDto;
import com.guilhermefgl.inter.model.User;

public class UserMapperTest {

	@Test
	public void testToDto() {
		User model = new User();
		model.setId(1l);
		model.setName("name");
		model.setEmail("email");

		UserDto dto = UserMapper.toDto(model);

		assertNotNull(dto);
		assertEquals(Long.valueOf(1l), dto.getId());
		assertEquals("name", dto.getName());
		assertEquals("email", dto.getEmail());
	}

	@Test
	public void testToModel() {
		UserDto dto = new UserDto();
		dto.setId(1l);
		dto.setName("name");
		dto.setEmail("email");

		User model = UserMapper.toModel(dto);

		assertNotNull(model);
		assertEquals(Long.valueOf(1l), model.getId());
		assertEquals("name", model.getName());
		assertEquals("email", model.getEmail());
	}

}
