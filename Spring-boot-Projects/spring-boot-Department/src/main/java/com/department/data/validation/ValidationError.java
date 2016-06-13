package com.department.data.validation;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Validates the error request for Department.
 * 
 * @author  Rajkumar
 * @version 1.0
 */

public class ValidationError {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> errors = new ArrayList();

	private final String errorMessage;

	public ValidationError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void addValidationError(String error) {
		errors.add(error);
	}

	public List<String> getErrors() {
		return errors;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
