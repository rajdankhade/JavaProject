package com.employee.data.validation;

import com.employee.data.model.Employee;
/**
 * Defines a single operation interface for validating input
 * 
 * @author  Rajkumar
 * @version 1.0
 */

public interface EmployeeRequestValidator {

	/**
	 * 
	 * 
	 * @param Employee
	 *            control to be validated.
	 * @param locale
	 *            the request locale.
	 * @return set of ExceptionDetail.
	 */
	ValidateMessage validate(Employee employee);
}
