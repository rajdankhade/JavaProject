package com.department.data.web;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.department.data.domain.Department;
import com.department.data.domain.Status;
import com.department.data.service.DepartmentService;
import com.department.data.utility.DepartmentConstants;
import com.department.data.validation.DepartmentValidator;
import com.department.data.validation.ValidateMessage;

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

	RestTemplate rt = new RestTemplate();

	ValidateMessage details = new ValidateMessage();
	
  /**
   * This is the method which return departments.
   * @return List<Department> This returns list of departments.
   * 
   */
	@ApiOperation(value = "Get all departments")
	@RequestMapping(value = "/", method = GET)
	public List<Department> getDepartments() {
		return departmentService.findAll();
	}

	/**
	 * This is the method which add departments.
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Add department")
	@RequestMapping(value = "/add/{id}/{name}/{salaryminrange}/{salarymaxrange}", method = POST)
	public @ResponseBody Status addDepartment(@PathVariable int id,
			@PathVariable String name, @PathVariable double salaryminrange,
			@PathVariable double salarymaxrange) {

		Department department = new Department();
		department.setId(id);
		department.setName(name);
		department.setSalaryminrange(salaryminrange);
		department.setSalarymaxrange(salarymaxrange);
		Department depId = departmentService.findOne(id);

		if (depId != null) {

			return new Status(DepartmentConstants.INVALID_CODE,
					DepartmentConstants.ERR_ID_EXIST);

		} else {
			details = departmentValidator.validate(department);

			if ((details != null)
					&& details.getErrorCriteria().equalsIgnoreCase(
							DepartmentConstants.ERROR_CRITERIA)) {
				return new Status(DepartmentConstants.NOTFOUND_CODE,
						details.getErrorMessage());
			} else {
				try {
					departmentService.saveDepartment(department);
					// return department;
					return new Status(DepartmentConstants.SUCCESS_CODE,
							DepartmentConstants.DEP_ADD_SCUCCESS_MESSAGE);

				} catch (Exception e) {
					return new Status(DepartmentConstants.INVALID_CODE,
							e.toString());
				}
			}
		}
	}

	/**
	 * This is the method which edit departments.
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Edit department")
	@RequestMapping(value = "/edit/{id}/{name}/{salaryminrange}/{salarymaxrange}", method = PUT)
	public @ResponseBody Status editDepartment(@PathVariable int id,
			@PathVariable String name, @PathVariable double salaryminrange,
			@PathVariable double salarymaxrange) {
		try {

			Department department = new Department();
			department.setId(id);
			department.setName(name);
			department.setSalaryminrange(salaryminrange);
			department.setSalarymaxrange(salarymaxrange);

			Department depId = departmentService.findOne(id);

			if (depId != null) {

				departmentService.saveDepartment(department);

				return new Status(DepartmentConstants.SUCCESS_CODE,
						DepartmentConstants.DEP_EDIT_SCUCCESS_MESSAGE);
			} else {
				return new Status(DepartmentConstants.INVALID_CODE,
						DepartmentConstants.INV_ID);

			}

		} catch (Exception e) {
			return new Status(DepartmentConstants.INVALID_CODE, e.toString());
		}

	}

	/**
	 * This is the method which get department on the basis of id.
	 * @return This returns department.
	 * 
	 */
	@ApiOperation(value = "Get department according to Id")
	@RequestMapping(value = "/{id}", method = GET)
	public Department getDepartment(@PathVariable int id) {
		Department department = departmentService.findOne(id);
		return department;
	}

	/**
	 * This is the method which delete department on the basis of id.
	 * @return Status This returns code and message.
	 * 
	 */
	@ApiOperation(value = "Delete department according to Id")
	@RequestMapping(value = "/delete/{id}", method = DELETE)
	public @ResponseBody Status deleteDepartment(@PathVariable int id) {
		Department department = new Department();
		department.setId(id);
		try {
			departmentService.delete(id);
			return new Status(DepartmentConstants.SUCCESS_CODE,
					DepartmentConstants.DEL_SUCCESS);
		} catch (Exception e) {
			return new Status(DepartmentConstants.INVALID_CODE,
					DepartmentConstants.ERR_DELETING);
		}
	}
	
	/**
	 * This is the method which verify department.
	 * @return String.
	 * 
	 */

	@ApiOperation(value = "Search department")
	@RequestMapping(value = "/search/department/{department}", method = GET)
	public String findByDepartment(@PathVariable String department) {
		List<Department> departmentRes = departmentService
				.findByDepartment(department);
		String availableDep = "false";
		if (departmentRes != null) {
			if (departmentRes.size() > 0) {
				availableDep = "true";
			} else {
				availableDep = "false";
			}
		}

		return availableDep;
	}

	/**
	 * This is the method which verify department and salary.
	 * @return String.
	 * 
	 */
	@ApiOperation(value = "Search department and salary")
	@RequestMapping(value = "/search/{department}/{salary}", method = GET)
	public String getDepartmentAndSalary(@PathVariable String department,
			@PathVariable double salary) {
		List<Department> departmentRes = departmentService
				.findByDepartmentAndSalary(department, salary);
		String availableSal = "false";
		if (departmentRes != null) {
			if (departmentRes.size() > 0) {
				availableSal = "true";
			} else {
				availableSal = "false";
			}
		}
		return availableSal;
	}

}
