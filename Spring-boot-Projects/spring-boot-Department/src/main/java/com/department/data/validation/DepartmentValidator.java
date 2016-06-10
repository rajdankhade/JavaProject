package com.department.data.validation;

import org.springframework.stereotype.Component;
import com.department.data.domain.Department;
import com.department.data.utility.DepartmentConstants;

/**
 * Validates the request Department.
 * 
 * @author  Rajkumar
 * @version 1.0
 */
@Component(value = "departmentValidator")
public class DepartmentValidator implements DepartmentRequestValidator {

	public ValidateMessage details = new ValidateMessage();

	@Override
	public ValidateMessage validate(Department department) {

		if (department.getId() < 0) {
			details.setErrorMessage(DepartmentConstants.INV_ID);
			details.setErrorCriteria(DepartmentConstants.ERROR_CRITERIA);
			return details;
		}

		if (department.getName().isEmpty()) {
			details.setErrorMessage(DepartmentConstants.EMP_NAME);
			details.setErrorCriteria(DepartmentConstants.ERROR_CRITERIA);
			return details;
		}

		if (department.getSalarymaxrange() < 0.0) {
			details.setErrorMessage(DepartmentConstants.INV_SAL_MAX_RAN);
			details.setErrorCriteria(DepartmentConstants.ERROR_CRITERIA);
			return details;
		}

		if (department.getSalaryminrange() < 0.0) {
			details.setErrorMessage(DepartmentConstants.INV_SAL_MIN_RAN);
			details.setErrorCriteria(DepartmentConstants.ERROR_CRITERIA);
			return details;
		}
		details.setErrorCriteria("true");
		return details;
	}
}
