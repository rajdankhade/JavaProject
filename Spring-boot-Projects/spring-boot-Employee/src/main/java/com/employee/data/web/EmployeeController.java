package com.employee.data.web;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.employee.data.model.Department;
import com.employee.data.model.Employee;
import com.employee.data.model.EmployeeDTO;
import com.employee.data.model.Status;
import com.employee.data.service.DepartmentService;
import com.employee.data.service.EmployeeService;
import com.employee.data.utility.EmployeeConstants;
import com.employee.data.validation.EmployeeValidator;
import com.employee.data.validation.ValidateMessage;

/**
 * This is Employee controller class for employee application.
 *
 * @author Rajkumar
 * @version 1.0
 * 
 */

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired(required = true)
	@Qualifier("employeeValidator")
	private EmployeeValidator employeeValidator;

	RestTemplate rt = new RestTemplate();

	ValidateMessage details = new ValidateMessage();

	/**
	 * This is the method which return employees.
	 * 
	 * @return List<Employee> This returns list of departments.
	 * 
	 */

	@ApiOperation(value = "Find all stored employees")
	@RequestMapping(value = "/", method = GET)
	// @RequestMapping(value = "/")
	public List<Employee> getEmployees() {
		return employeeService.findAll();
	}

	/**
	 * This is the method which get employee on the basis of id.
	 * 
	 * @return This returns employee.
	 * 
	 */
	@ApiOperation(value = "Find employee according to id")
	@RequestMapping(value = "/{id}", method = GET)
	public List<EmployeeDTO> getEmployeeId(@PathVariable int id) {

		Employee employee = employeeService.findOne(id);

		List<EmployeeDTO> employeeDTO = new ArrayList<EmployeeDTO>();

		Department department = departmentService.findByDepartment(employee
				.getDepartment());
		EmployeeDTO dto = new EmployeeDTO();
		dto.setSalaryminrange(department.getSalaryminrange());
		dto.setSalarymaxrange(department.getSalarymaxrange());
		dto.setId(id);
		dto.setName(employee.getName());
		dto.setManagername(employee.getManagerName());
		dto.setDepartment(employee.getDepartment());
		dto.setSalary(employee.getSalary());
		employeeDTO.add(dto);
		return employeeDTO;
	}

	/**
	 * This is the method which edit employees.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Edit Employee according to Id")
	@RequestMapping(value = "/edit/{id}/{name}/{managerName}/{department}/{salary}", method = PUT)
	public @ResponseBody Status editEmployee(@PathVariable int id,
			@PathVariable String name, @PathVariable String managerName,
			@PathVariable String department, @PathVariable double salary) {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setManagerName(managerName);
		employee.setDepartment(department);
		employee.setSalary(salary);
		Employee empId = employeeService.findOne(id);

		if (empId != null) {

			details = employeeValidator.validate(employee);

			if ((details != null)
					&& details.getErrorCriteria().equalsIgnoreCase(
							EmployeeConstants.ERROR_CRITERIA)) {
				return new Status(EmployeeConstants.INVALID_CODE,
						details.getErrorMessage());
			} else {

				List<Employee> employeeRes = employeeService
						.findByManagerName(managerName);

				String availableManager = "false";
				if (employeeRes != null) {
					if (employeeRes.size() > 0) {
						availableManager = "true";
					} else {
						availableManager = "false";
					}
				}

				if (availableManager.equalsIgnoreCase("true")) {
					String depName = rt.getForObject(
							EmployeeConstants.CHECK_FOR_DEPARTMENT
									+ employee.getDepartment(), String.class);

					if (depName != null) {
						if ((depName.equalsIgnoreCase("true"))) {

							String salRes = rt.getForObject(
									EmployeeConstants.CHECK_FOR_DEP_SAL
											+ employee.getDepartment() + "/"
											+ employee.getSalary(),
									String.class);

							if (salRes.equalsIgnoreCase("false")) {
								return new Status(
										EmployeeConstants.NOTFOUND_CODE,
										EmployeeConstants.EMP_SAL_RANGE_MESSAGE);
							}

						} else {
							return new Status(EmployeeConstants.NOTFOUND_CODE,
									EmployeeConstants.EMP_ADD_VAL_DEP_MESSAGE);
						}
					}
				} else {
					return new Status(EmployeeConstants.NOTFOUND_CODE,
							EmployeeConstants.EMP_ADD_VAL_MAN_MESSAGE);
				}
				employeeService.saveEmployee(employee);
				return new Status(EmployeeConstants.SUCCESS_CODE,
						EmployeeConstants.EMP_EDIT_SCUCCESS_MESSAGE);
			}
		} else {
			return new Status(EmployeeConstants.INVALID_CODE,
					EmployeeConstants.INV_ID);

		}

	}

	/**
	 * This is the method which delete employee on the basis of id.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */

	@ApiOperation(value = "Delete Employee according to Id")
	@RequestMapping(value = "/delete/{id}", method = DELETE)
	public @ResponseBody Status deleteEmployee(@PathVariable int id) {
		Employee employee = new Employee();
		employee.setId(id);
		try {
			employeeService.delete(id);
			return new Status(EmployeeConstants.SUCCESS_CODE,
					EmployeeConstants.DEL_SUCCESS);
		} catch (Exception e) {
			return new Status(EmployeeConstants.INVALID_CODE,
					EmployeeConstants.ERR_DELETING);
		}
	}

	/**
	 * This is the method which add employees.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */

	@ApiOperation(value = "Add New Employee")
	@RequestMapping(value = "/add/{id}/{name}/{managerName}/{department}/{salary}", method = POST)
	public @ResponseBody Status addedEmployee(@PathVariable int id,
			@PathVariable String name, @PathVariable String managerName,
			@PathVariable String department, @PathVariable double salary) {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setManagerName(managerName);
		employee.setDepartment(department);
		employee.setSalary(salary);

		Employee empId = employeeService.findOne(id);

		if (empId != null) {

			return new Status(EmployeeConstants.INVALID_CODE,
					EmployeeConstants.ERR_ID_EXIST);

		} else {

			details = employeeValidator.validate(employee);

			if ((details != null)
					&& details.getErrorCriteria().equalsIgnoreCase(
							EmployeeConstants.ERROR_CRITERIA)) {
				return new Status(EmployeeConstants.INVALID_CODE,
						details.getErrorMessage());
			} else {

				List<Employee> employeeRes = employeeService
						.findByManagerName(managerName);

				String availableManager = "false";
				if (employeeRes != null) {
					if (employeeRes.size() > 0) {
						availableManager = "true";
					} else {
						availableManager = "false";
					}
				}

				if (availableManager.equalsIgnoreCase("true")) {
					String depName = rt.getForObject(
							EmployeeConstants.CHECK_FOR_DEPARTMENT
									+ employee.getDepartment(), String.class);

					if (depName != null) {
						if ((depName.equalsIgnoreCase("true"))) {

							String salRes = rt.getForObject(
									EmployeeConstants.CHECK_FOR_DEP_SAL
											+ employee.getDepartment() + "/"
											+ employee.getSalary(),
									String.class);

							if (salRes.equalsIgnoreCase("false")) {
								return new Status(
										EmployeeConstants.NOTFOUND_CODE,
										EmployeeConstants.EMP_SAL_RANGE_MESSAGE);
							}

						} else {
							return new Status(EmployeeConstants.NOTFOUND_CODE,
									EmployeeConstants.EMP_ADD_VAL_DEP_MESSAGE);
						}
					}
				} else {
					return new Status(EmployeeConstants.NOTFOUND_CODE,
							EmployeeConstants.EMP_ADD_VAL_MAN_MESSAGE);
				}
				employeeService.saveEmployee(employee);
				return new Status(EmployeeConstants.SUCCESS_CODE,
						EmployeeConstants.EMP_ADD_SCUCCESS_MESSAGE);
			}
		}

	}
}
