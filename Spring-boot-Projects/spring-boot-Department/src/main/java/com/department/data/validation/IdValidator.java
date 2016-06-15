package com.department.data.validation;

import org.h2.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import com.department.data.utility.DepartmentConstants;

/**
 * Validates the request ID validator.
 * 
 * @author  Rajkumar
 * @version 1.0
 */

@Validated
@Component(value = "idValidator")
public class IdValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String id = (String) target;
		
		if (!StringUtils.isNumber(id)) {			
			errors.rejectValue("id", DepartmentConstants.INV_ID);
		}
	}
}