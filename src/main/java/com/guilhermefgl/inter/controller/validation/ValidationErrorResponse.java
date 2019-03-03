package com.guilhermefgl.inter.controller.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorResponse {

	private Map<String, List<String>> violations = new HashMap<>();

	public Map<String, List<String>> getViolations() {
		return violations;
	}

	public void setViolations(Map<String, List<String>> violations) {
		this.violations = violations;
	}

	public void addErrorMessage(String key, String message) {
		if (violations.get(key) == null) {
			violations.put(key, new ArrayList<String>());
		}
		violations.get(key).add(message);
	}

}