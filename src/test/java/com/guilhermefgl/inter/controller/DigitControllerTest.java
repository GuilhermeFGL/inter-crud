package com.guilhermefgl.inter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DigitControllerTest {

	private static final String URL_BASE = "/api/digitoUnico";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

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
	public void testCreate_whenNisValid_thenReturnsStatus200() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreate_whenKandNisValid_thenReturnsStatus200() throws Exception {
		DigitDto input = new DigitDto();
		input.setN("123");
		input.setK(5);

		mvc.perform(
				post(URL_BASE).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk());
	}

}
