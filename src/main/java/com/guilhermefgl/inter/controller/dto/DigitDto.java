package com.guilhermefgl.inter.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class DigitDto {

	@Min(1)
	@Max(1000000)
	private Integer k;

	@NotEmpty
	@Min(1)
	@Pattern(regexp = "\\d+")
	private String n;

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

}
