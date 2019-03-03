package com.guilhermefgl.inter.util.validation;

public class Violation {

	private String fieldName;

	private String message;

	public Violation(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return message;
	}

}