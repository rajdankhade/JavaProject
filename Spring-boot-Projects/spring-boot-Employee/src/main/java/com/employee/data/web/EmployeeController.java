package com.employee.data.web;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.employee.data.exception.EmployeeNotFoundException;
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
	@ApiResponses(value = {
			@ApiResponse(code = EmployeeConstants.SUCCESS_CODE, message = EmployeeConstants.GET_SUCCESS),
			@ApiResponse(code = EmployeeConstants.NOTFOUND_CODE, message = EmployeeConstants.RESULT_EXC) })
	@RequestMapping(value = "/", method = GET)
	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> listEmployee = null;
		try {
			listEmployee = employeeService.findAll();
			return new ResponseEntity<List<Employee>>(listEmployee, OK);
		} catch (EmployeeNotFoundException e) {
			return new ResponseEntity<List<Employee>>(listEmployee, NOT_FOUND);
		}
	}

	/**
	 * This is the method which get employee on the basis of id.
	 * 
	 * @return This returns employee.
	 * 
	 */
	@ApiOperation(value = "Find employee according to id")
	@ApiResponses(value = {
			@ApiResponse(code = EmployeeConstants.SUCCESS_CODE, message = EmployeeConstants.GET_SUCCESS),
			@ApiResponse(code = EmployeeConstants.NOTFOUND_CODE, message = EmployeeConstants.NOT_FOUND_ID_GET) })
	@RequestMapping(value = "/{id}", method = GET)
	public ResponseEntity<List<EmployeeDTO>> getEmployeeId(@PathVariable String id) {
		
		Employee emp = new Employee();
		emp.setId(Integer.parseInt(id));
		
		details = employeeValidator.validate(emp);

		if ((details != null)
				&& details.getErrorCriteria().equalsIgnoreCase(
						EmployeeConstants.ERROR_CRITERIA)) {
			return new ResponseEntity<List<EmployeeDTO>>(NOT_FOUND);
		} 
		try {
			Employee employee = employeeService.findOne(Integer.parseInt(id));
			if (employee == null) {				
				throw new EmployeeNotFoundException();
			}

			List<EmployeeDTO> employeeDTO = new ArrayList<EmployeeDTO>();

			Department department = departmentService.findByDepartment(employee
					.getDepartment());
			EmployeeDTO dto = new EmployeeDTO();
			dto.setSalaryminrange(department.getSalaryminrange());
			dto.setSalarymaxrange(department.getSalarymaxrange());
			dto.setId(Integer.parseInt(id));
			dto.setName(employee.getName());
			dto.setManagername(employee.getManagerName());
			dto.setDepartment(employee.getDepartment());
			dto.setSalary(employee.getSalary());
			employeeDTO.add(dto);
			return new ResponseEntity<List<EmployeeDTO>>(employeeDTO, OK);

		} catch (EmployeeNotFoundException e) {			
			return new ResponseEntity<List<EmployeeDTO>>(NOT_FOUND);
		}
	}

	/**
	 * This is the method which edit employees.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Edit Employee according to Id")
	@RequestMapping(value = "/{id}/{name}/{managerName}/{department}/{salary}", method = PUT)
	public @ResponseBody Status editEmployee(@PathVariable String id,
			@PathVariable String name, @PathVariable String managerName,
			@PathVariable String department, @PathVariable double salary) {
		
		
		Employee employee = new Employee();
		employee.setId(Integer.parseInt(id));
		employee.setName(name);
		employee.setManagerName(managerName);
		employee.setDepartment(department);
		employee.setSalary(salary);
		Employee empId = null;
		try {
			empId = employeeService.findOne(Integer.parseInt(id));
		} catch (EmployeeNotFoundException e) {
			return new Status(EmployeeConstants.NOTFOUND_CODE,
					EmployeeConstants.INV_ID);
		}

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
					ResponseEntity<Department> depName = rt.getForEntity(
							EmployeeConstants.CHECK_FOR_DEPARTMENT
									+ employee.getDepartment(),
							Department.class);
					if (depName != null) {						
						if (depName.getStatusCode() == OK) {						
							ResponseEntity<Department> salRes = rt
									.getForEntity(
											EmployeeConstants.CHECK_FOR_DEP_SAL
													+ employee.getDepartment()
													+ "/"
													+ employee.getSalary(),
											Department.class);						
							if (salRes.getStatusCode() == HttpStatus.NON_AUTHORITATIVE_INFORMATION) {
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
	@ApiResponses(value = {
			@ApiResponse(code = EmployeeConstants.SUCCESS_CODE, message = EmployeeConstants.DEL_SUCCESS),
			@ApiResponse(code = EmployeeConstants.NOTFOUND_CODE, message = EmployeeConstants.NOT_FOUND_ID_DELETE) })
	@RequestMapping(value = "/{id}", method = DELETE)
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String id) {
		
		Employee emp = new Employee();
		emp.setId(Integer.parseInt(id));
		
		details = employeeValidator.validate(emp);
		Employee employee = null;
		// employee.setId(id);
		try {
			employee = employeeService.findOne(Integer.parseInt(id));
			if (employee == null) {				
				throw new EmployeeNotFoundException();
			}
			employeeService.delete(Integer.parseInt(id));
			/*
			 * return new Status(EmployeeConstants.SUCCESS_CODE,
			 * EmployeeConstants.DEL_SUCCESS);
			 */
			return new ResponseEntity<Employee>(NO_CONTENT);
		} catch (EmployeeNotFoundException e) {
			return new ResponseEntity<Employee>(NOT_FOUND);
		}
	}

	/**
	 * This is the method which add employees.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */

	@ApiOperation(value = "Add New Employee")
	@RequestMapping(value = "/{id}/{name}/{managerName}/{department}/{salary}", method = POST)
	public @ResponseBody Status addedEmployee(@PathVariable String id,
			@PathVariable String name, @PathVariable String managerName,
			@PathVariable String department, @PathVariable double salary) {
		
		
		Employee employee = new Employee();
		employee.setId(Integer.parseInt(id));
		employee.setName(name);
		employee.setManagerName(managerName);
		employee.setDepartment(department);
		employee.setSalary(salary);

		Employee empId = null;
		try {
			empId = employeeService.findOne(Integer.parseInt(id));
		} catch (EmployeeNotFoundException e) {
			return new Status(EmployeeConstants.NOTFOUND_CODE,
					EmployeeConstants.ERR_ID_EXIST);
		}

		if(empId != null) {
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
					ResponseEntity<Department> depName = rt.getForEntity(
							EmployeeConstants.CHECK_FOR_DEPARTMENT
									+ employee.getDepartment(),
							Department.class);
					if (depName != null) {
						if (depName.getStatusCode() == OK) {
							ResponseEntity<Department> salRes = rt
									.getForEntity(
											EmployeeConstants.CHECK_FOR_DEP_SAL
													+ employee.getDepartment()
													+ "/"
													+ employee.getSalary(),
											Department.class);
							if (salRes.getStatusCode() == HttpStatus.NON_AUTHORITATIVE_INFORMATION) {
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