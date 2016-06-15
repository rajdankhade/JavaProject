package com.department.data.validation;

import com.department.data.domain.Department;

/**
 * Defines a single operation interface for validating input
 * 
 * @author  Rajkumar
 * @version 1.0
 */
public interface DepartmentRequestValidator {

	/**
	 * 
	 * 
	 * @param Department
	 *            control to be validated.
	 * @param locale
	 *            the request locale.
	 * @return set of ExceptionDetail.
	 */
	ValidateMessage validate(Department department);
}
