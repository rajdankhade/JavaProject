package com.employee.data.validation;

import org.springframework.stereotype.Component;
import com.employee.data.model.Employee;
import com.employee.data.utility.EmployeeConstants;

/**
 * Validates the request Employee.
 * 
 * @author  Rajkumar
 * @version 1.0
 */

@Component(value = "employeeValidator")
public class EmployeeValidator implements EmployeeRequestValidator {

	public ValidateMessage details = new ValidateMessage();

	@Override
	public ValidateMessage validate(Employee employee) {
		
		if (employee.getId() < 0) {
			details.setErrorMessage(EmployeeConstants.INV_ID);
			details.setErrorCriteria(EmployeeConstants.ERROR_CRITERIA);
			return details;
		}

		if (employee.getName().isEmpty()) {
			details.setErrorMessage(EmployeeConstants.EMP_NAME);
			details.setErrorCriteria(EmployeeConstants.ERROR_CRITERIA);
			return details;
		}

		if (employee.getManagerName().isEmpty()) {
			details.setErrorMessage(EmployeeConstants.EMP_MAN_NAME);
			details.setErrorCriteria(EmployeeConstants.ERROR_CRITERIA);
			return details;
		}

		if (employee.getDepartment().isEmpty()) {
			details.setErrorMessage(EmployeeConstants.EMP_DEP);
			details.setErrorCriteria(EmployeeConstants.ERROR_CRITERIA);
			return details;
		}

		if (employee.getSalary() == 0) {
			details.setErrorMessage(EmployeeConstants.INV_SAL);
			details.setErrorCriteria(EmployeeConstants.ERROR_CRITERIA);
			return details;
		}
		
		details.setErrorCriteria("true");
		return details;
	}
}
