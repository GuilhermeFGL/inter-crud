package com.guilhermefgl.inter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.guilhermefgl.inter.controller.dto.DigitDto;
import com.guilhermefgl.inter.model.Digit;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.model.repositories.DigitRepository;
import com.guilhermefgl.inter.model.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DigitControllerTest {

	private static final String URL_BASE = "/api/digitoUnico";
	private static final String HEADER_PARAM_USER = "user";
	private static final int ID_INVALID = -1;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DigitRepository digitRepository;

	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setName("name");
		user.setEmail("email");
		user = userRepository.save(user);
	}

	@Test
	public void testList_whenEmpty_thenReturnStatus204() throws Exception {
		mvc.perform(get(URL_BASE).contentType(MediaType.APPLICATION_JSON).header(HEADER_PARAM_USER, ID_INVALID))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testList_whenNotEmpty_thenReturnStatus200() throws Exception {
		Digit digit = new Digit();
		digit.setK(1);
		digit.setN("123");
		digit.setDigit(6);
		digit.setUser(user);
		digitRepository.save(digit);

		mvc.perform(get(URL_BASE).contentType(MediaType.APPLICATION_JSON).header(HEADER_PARAM_USER, user.getId()))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreate_whenNull_thenReturnsStatus400() throws Exception {
		DigitDto input = new DigitDto();

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenNisInvalidText_thenReturnsStatus400() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("abc");

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenNisInvalidNumber_thenReturnsStatus400() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("0");

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenNisInvalidEmpty_thenReturnsStatus400() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("");

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenKisInvalidMin_thenReturnsStatus400() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");
		input.setK(0);

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenKisInvalidMax_thenReturnsStatus400() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");
		input.setK(1000001);

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreate_whenUserNotFound_thenReturnStatus403() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");
		input.setK(5);

		mvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON).header(HEADER_PARAM_USER, ID_INVALID)
				.content(objectMapper.writeValueAsString(input))).andExpect(status().isForbidden());
	}

	@Test
	public void testCreate_whenNisValid_thenReturnsStatus201() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isCreated());
	}

	@Test
	public void testCreate_whenKandNisValid_thenReturnsStatus201() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");
		input.setK(5);

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isCreated());
	}

	@Test
	public void testCreate_whenUserFound_thenReturnStatus201() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");
		input.setK(5);

		mvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON).header(HEADER_PARAM_USER, user.getId())
				.content(objectMapper.writeValueAsString(input))).andExpect(status().isCreated());
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
		digitRepository.deleteAll();
	}

}
