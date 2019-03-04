package com.guilhermefgl.inter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilhermefgl.inter.controller.dto.UserDto;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.model.dao.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

	private static final String URL_BASE = "/api/usuario";
	private static final String PARAMETER_ID = "/{id}";
	private static final int ID_INVALID = -1;

	@Autowired
	private UserRepository repository;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setName("name");
		user.setEmail("email");
		user = repository.save(user);
	}

	@Test
	public void testList_whenNotEmpty_thenReturnsStatus200() throws Exception {
		mvc.perform(get(URL_BASE).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testList_whenEmpty_thenReturnsStatus204() throws Exception {
		repository.deleteAll();
		mvc.perform(get(URL_BASE).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void testFind_whenNotFound_thenReturnsStatus404() throws Exception {
		mvc.perform(get(URL_BASE.concat(PARAMETER_ID), ID_INVALID).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testFind_whenFound_thenReturnsStatus200() throws Exception {
		mvc.perform(get(URL_BASE.concat(PARAMETER_ID), user.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreate_whenInvalid_thenReturnsStatus400() throws Exception {
		mvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDto()))).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenNameInvalid_thenReturnsStatus400() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("");
		userDto.setEmail("email 2");

		mvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenEmailInvalid_thenReturnsStatus400() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name 2");
		userDto.setEmail("");

		mvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenValid_thenReturnsStatus201() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name 2");
		userDto.setEmail("email 2");

		mvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isCreated());
	}

	@Test
	public void testUpdate_whenInvalid_thenReturnsStatus400() throws Exception {
		mvc.perform(put(URL_BASE.concat(PARAMETER_ID), user.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDto()))).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdate_whenNameInvalid_thenReturnsStatus400() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("");
		userDto.setEmail("email 2");

		mvc.perform(put(URL_BASE.concat(PARAMETER_ID), user.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdate_whenEmailInvalid_thenReturnsStatus400() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name 2");
		userDto.setEmail("");

		mvc.perform(put(URL_BASE.concat(PARAMETER_ID), user.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdate_whenValidAndNotFound_thenReturnsStatus204() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name 2");
		userDto.setEmail("email 2");

		mvc.perform(put(URL_BASE.concat(PARAMETER_ID), ID_INVALID).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isNoContent());
	}

	@Test
	public void testUpdate_whenValidAndFound_thenReturnsStatus200() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setName("name 2");
		userDto.setEmail("email 2");

		mvc.perform(put(URL_BASE.concat(PARAMETER_ID), user.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))).andExpect(status().isOk());
	}

	@Test
	public void testDelete_whenNotFound_thenReturnsStatus204() throws Exception {
		mvc.perform(delete(URL_BASE.concat(PARAMETER_ID), ID_INVALID).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testDelete_whenFound_thenReturnsStatus200() throws Exception {
		mvc.perform(delete(URL_BASE.concat(PARAMETER_ID), user.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@After
	public void tearDown() {
		repository.deleteAll();
	}

}
