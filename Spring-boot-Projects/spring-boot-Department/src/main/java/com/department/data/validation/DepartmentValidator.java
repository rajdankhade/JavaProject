package com.department.data.validation;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import com.department.data.domain.Department;
import com.department.data.utility.DepartmentConstants;

@Validated
@Component(value = "departmentValidator")
public class DepartmentValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		// just validate the Customer instances
		return Department.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {		

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "400",
				"Field id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "400",
				"Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salaryminrange",
				"400", "Field salaryminrange is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salarymaxrange",
				"400", "Field salarymaxrange is required.");

		Department department = (Department) target;
		
		
		
		
		if (department.getId() != null) {
			if (department.getId().intValue() < 0) {
				errors.rejectValue("id", DepartmentConstants.INV_ID);
			}else 
		
		
		if (department.getName() != null) {
			if (department.getName().equalsIgnoreCase("{name}")) {
				errors.rejectValue("name", DepartmentConstants.EMP_NAME);
			}
		}
		
		
		if (department.getSalaryminrange() != null) {
			if (department.getSalaryminrange() < 0.0) {
				errors.rejectValue("salaryminrange",
						DepartmentConstants.INV_SAL_MIN_RAN);

			}
		}
		if (department.getSalarymaxrange() != null) {
			if (department.getSalarymaxrange() < 0.0) {
				errors.rejectValue("salarymaxrange",
						DepartmentConstants.INV_SAL_MAX_RAN);

			}
		}

		if (department.getSalaryminrange() != null
				&& department.getSalarymaxrange() != null) {
			if (department.getSalaryminrange() > department.getSalarymaxrange()) {
				errors.rejectValue("salaryminrange",
						DepartmentConstants.INV_SAL_MIN_RAN);

			}
		}

	}
	}
}