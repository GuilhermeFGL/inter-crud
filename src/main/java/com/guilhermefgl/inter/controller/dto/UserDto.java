package com.guilhermefgl.inter.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class UserDto {

	@JsonProperty
	@JsonPropertyDescription("chave única do usuário")
	private Long id;

	@NotNull
	@NotEmpty
	@JsonProperty
	@JsonPropertyDescription("nome do usuario")
	private String name;

	@NotNull
	@NotEmpty
	@JsonProperty
	@JsonPropertyDescription("email do usuario")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
