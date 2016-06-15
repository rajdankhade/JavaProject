package com.department.data.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.department.data.domain.Department;
import com.department.data.exception.DepartmentNotFoundException;
import com.department.data.service.DepartmentService;
import com.department.data.utility.DepartmentConstants;
import com.department.data.validation.DepartmentValidator;
import com.department.data.validation.IdValidator;

/**
 * This is Department controller class for department application.
 *
 * @author Rajkumar
 * @version 1.0
 * 
 */
@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@Autowired(required = true)
	@Qualifier("departmentValidator")
	private DepartmentValidator departmentValidator;

	@Autowired(required = true)
	@Qualifier("idValidator")
	private IdValidator idValidator;

	RestTemplate rt = new RestTemplate();

	/**
	 * This is the method which return departments.
	 * 
	 * @return List<Department> This returns list of departments.
	 * 
	 */
	@ApiOperation(value = "Get all departments")
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CODE, message = DepartmentConstants.GET_SUCCESS),
			@ApiResponse(code = DepartmentConstants.NOTFOUND_CODE, message = DepartmentConstants.RESULT_EXC) })
	@RequestMapping(value = "/", method = GET)
	public ResponseEntity<List<Department>> getDepartments() {
		List<Department> listDepartment = null;
		try {
			listDepartment = departmentService.findAll();
			return new ResponseEntity<List<Department>>(listDepartment, OK);
		} catch (DepartmentNotFoundException e) {
			return new ResponseEntity<List<Department>>(listDepartment,
					NOT_FOUND);
		}
	}

	@ApiOperation(value = "Add department")
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CREATED_CODE, message = DepartmentConstants.DEP_ADD_SCUCCESS_MESSAGE),
			@ApiResponse(code = DepartmentConstants.NOTFOUND_CODE, message = DepartmentConstants.INVALID_ADD),
			@ApiResponse(code = DepartmentConstants.INVALID_CODE, message = DepartmentConstants.INVALID_INPUT) })
	@RequestMapping(value = "/{id}/{name}/{salaryminrange}/{salarymaxrange}", method = POST)
	public ResponseEntity<Department> addDepartment(
			@Validated @ModelAttribute("dep") Department depVal,
			BindingResult result) {
		Department depId = null;
		departmentValidator.validate(depVal, result);

		if (result.hasErrors()) {
			for (Object object : result.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					/*
					 * return new Status(DepartmentConstants.INVALID_CODE,
					 * fieldError.getCode());
					 */
					return new ResponseEntity<Department>(BAD_REQUEST);
				}
			}
		} else {

			try {
				depId = departmentService.findOne(depVal.getId());
			} catch (DepartmentNotFoundException e1) {
				return new ResponseEntity<Department>(NOT_FOUND);
			}

			if (depId != null) {
				return new ResponseEntity<Department>(NOT_FOUND);
			} else {
				try {
					List<Department> departmentRes = departmentService
							.findByDepartment(depVal.getName());

					if (departmentRes != null) {
						if (departmentRes.size() > 0) {
							return new ResponseEntity<Department>(NOT_FOUND);
						}
					}
					departmentService.saveDepartment(depVal);
				} catch (Exception e) {
					return new ResponseEntity<Department>(NOT_FOUND);
				}
			}
		}
		// return department;
		return new ResponseEntity<Department>(CREATED);
	}

	/**
	 * This is the method which edit departments.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Edit department")
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CREATED_CODE, message = DepartmentConstants.DEP_EDIT_SCUCCESS_MESSAGE),
			@ApiResponse(code = DepartmentConstants.NOTFOUND_CODE, message = DepartmentConstants.INVALID_EDIT),
			@ApiResponse(code = DepartmentConstants.INVALID_CODE, message = DepartmentConstants.INVALID_INPUT) })
	@RequestMapping(value = "/{id}/{name}/{salaryminrange}/{salarymaxrange}", method = PUT)
	public ResponseEntity<Department> editDepartment(
			@Validated @ModelAttribute("dep") Department depVal,
			BindingResult result) {

		departmentValidator.validate(depVal, result);

		if (result.hasErrors()) {
			for (Object object : result.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					/*
					 * return new Status(DepartmentConstants.INVALID_CODE,
					 * fieldError.getCode());
					 */
					return new ResponseEntity<Department>(BAD_REQUEST);
				}
			}
		} else {
			try {

				Department depId = departmentService.findOne(depVal.getId());
				if (depId != null) {
					List<Department> departmentRes = departmentService
							.findByDepartment(depVal.getName());

					if (departmentRes != null) {
						if (departmentRes.size() > 0) {
							return new ResponseEntity<Department>(NOT_FOUND);
						}
					}
					departmentService.saveDepartment(depVal);
					return new ResponseEntity<Department>(CREATED);
				} else {
					return new ResponseEntity<Department>(NOT_FOUND);
				}
			} catch (Exception e) {
				return new ResponseEntity<Department>(NOT_FOUND);
			}
		}
		return new ResponseEntity<Department>(CREATED);
	}

	@ApiOperation(value = "Get department according to Id")
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CODE, message = DepartmentConstants.GET_SUCCESS),
			@ApiResponse(code = DepartmentConstants.NOTFOUND_CODE, message = DepartmentConstants.NOT_FOUND_ID_GET) })
	@RequestMapping(value = "/{id}", method = GET)
	public ResponseEntity<Department> getDepartment(
			@RequestParam("id") String id) {

		if (!StringUtils.isNumber(id)) {
			return new ResponseEntity<Department>(NOT_FOUND);
		}

		Department department = null;
		try {
			department = departmentService.findOne(Integer.parseInt(id));
			if (department == null) {
				throw new DepartmentNotFoundException();
			}
			return new ResponseEntity<Department>(department, OK);
		} catch (DepartmentNotFoundException e) {
			return new ResponseEntity<Department>(NOT_FOUND);
		}
	}

	/**
	 * This is the method which delete department on the basis of id.
	 * 
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Delete department according to Id")
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CODE, message = DepartmentConstants.DEL_SUCCESS),
			@ApiResponse(code = DepartmentConstants.NOTFOUND_CODE, message = DepartmentConstants.NOT_FOUND_ID_DELETE) })
	@RequestMapping(value = "/{id}", method = DELETE)
	public ResponseEntity<Department> deleteDepartment(@PathVariable String id) {

		if (!StringUtils.isNumber(id)) {
			return new ResponseEntity<Department>(NOT_FOUND);
		}
		Department department = null;
		try {
			department = departmentService.findOne(Integer.parseInt(id));
			if (department == null) {
				throw new DepartmentNotFoundException();
			}
			departmentService.delete(Integer.parseInt(id));
			return new ResponseEntity<Department>(OK);
		} catch (DepartmentNotFoundException e) {
			return new ResponseEntity<Department>(NOT_FOUND);
		}
	}

	/**
	 * This is the method which verify department.
	 * 
	 * @return String.
	 * 
	 */
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CODE, message = ""),
			@ApiResponse(code = DepartmentConstants.NON_AUTHORITATIVE_INFORMATION, message = DepartmentConstants.ISSUE_SEARCH_RESULT) })
	@ApiOperation(value = "Search department")
	@RequestMapping(value = "/search/{department}", method = GET)
	public ResponseEntity<Department> findByDepartment(
			@PathVariable String department) {
		List<Department> departmentRes = departmentService
				.findByDepartment(department);

		try {
			if (departmentRes != null) {
				if (departmentRes.size() > 0) {
					return new ResponseEntity<Department>(OK);
				} else {
					return new ResponseEntity<Department>(
							HttpStatus.NON_AUTHORITATIVE_INFORMATION);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Department>(
					HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		return new ResponseEntity<Department>(
				HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}

	/**
	 * This is the method which verify department and salary.
	 * 
	 * @return String.
	 * 
	 */
	@ApiOperation(value = "Search department and salary")
	@ApiResponses(value = {
			@ApiResponse(code = DepartmentConstants.SUCCESS_CODE, message = ""),
			@ApiResponse(code = DepartmentConstants.INVALID_CODE, message = DepartmentConstants.INV_SAL),
			@ApiResponse(code = DepartmentConstants.NON_AUTHORITATIVE_INFORMATION, message = DepartmentConstants.ISSUE_SEARCH_DEPARTMENT_SALARY) })
	@RequestMapping(value = "/search/{department}/{salary}", method = GET)
	public ResponseEntity<Department> getDepartmentAndSalary(
			@PathVariable String department, @PathVariable String salary) {	

				
		List<Department> departmentRes = departmentService
				.findByDepartmentAndSalary(department, Double.parseDouble(salary));// or we can use
																// status code
		try {
			if (departmentRes != null) {
				if (departmentRes.size() > 0) {
					return new ResponseEntity<Department>(OK);
				} else {
					return new ResponseEntity<Department>(
							HttpStatus.NON_AUTHORITATIVE_INFORMATION);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Department>(
					HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}

		return new ResponseEntity<Department>(
				HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}
}