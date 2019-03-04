package com.guilhermefgl.inter.util.mapper;

import com.guilhermefgl.inter.controller.dto.DigitDto;
import com.guilhermefgl.inter.model.Digit;

public final class DigitMapper {

	private DigitMapper() {
	}

	public static DigitDto toDto(Digit model) {
		DigitDto dto = new DigitDto();
		dto.setK(model.getK());
		dto.setN(model.getN());
		dto.setResult(model.getDigit());
		return dto;
	}

	public static Digit toModel(DigitDto dto) {
		Digit model = new Digit();
		model.setK(dto.getK());
		model.setN(dto.getN());
		model.setDigit(dto.getResult());
		return model;
	}

}
