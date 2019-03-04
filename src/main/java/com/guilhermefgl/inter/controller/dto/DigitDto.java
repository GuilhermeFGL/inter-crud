package com.guilhermefgl.inter.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.guilhermefgl.inter.util.Constants;

public class DigitDto {

	@Min(1)
	@Max(1000000)
	@JsonProperty
	@JsonPropertyDescription("inteiro representando o número de vezes da concatenação")
	private Integer k;

	@NotNull
	@NotEmpty
	@Min(1)
	@Pattern(regexp = Constants.REGEX_DIGIT)
	@JsonProperty
	@JsonPropertyDescription("string representado um inteiro")
	private String n;

	private Integer result;

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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((k == null) ? 0 : k.hashCode());
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DigitDto other = (DigitDto) obj;
		if (k == null) {
			if (other.k != null)
				return false;
		} else if (!k.equals(other.k))
			return false;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		return true;
	}

}
