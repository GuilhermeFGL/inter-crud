package com.guilhermefgl.inter.util.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.guilhermefgl.inter.controller.dto.DigitDto;
import com.guilhermefgl.inter.model.Digit;

public class DigitMapperTest {

	@Test
	public void testToDto() {
		Digit model = new Digit();
		model.setK(1);
		model.setN("2");
		model.setDigit(3);

		DigitDto dto = DigitMapper.toDto(model);

		assertNotNull(dto);
		assertEquals(Integer.valueOf(1), dto.getK());
		assertEquals("2", dto.getN());
		assertEquals(Integer.valueOf(3), dto.getResult());
	}

	@Test
	public void testToModel() {
		DigitDto dto = new DigitDto();
		dto.setK(1);
		dto.setN("2");
		dto.setResult(3);

		Digit model = DigitMapper.toModel(dto);

		assertNotNull(model);
		assertEquals(Integer.valueOf(1), model.getK());
		assertEquals("2", model.getN());
		assertEquals(Integer.valueOf(3), model.getDigit());
	}

}
