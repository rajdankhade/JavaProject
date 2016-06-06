package com.departmentapp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import com.departmentapp.domain.Department;
import com.departmentapp.dto.DepartmentDTO;
import com.departmentapp.service.DepartmentService;
import com.departmentapp.service.EmployeeService;

/**
 * DepartmentController handles all department related request.
 * 
 * @author Rajkumar
 */
@Controller
@RequestMapping("/main/record")
public class DepartmentController {

	protected static Logger logger = Logger.getLogger("DepartmentController");

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Retrieves the "Records" page
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getRecords(Model model) {
		logger.debug("Received request to show records page");

		// Retrieve all Departments
		List<Department> departments = departmentService.getAll();

		// Prepare model object
		List<DepartmentDTO> departmentsDTO = new ArrayList<DepartmentDTO>();

		for (Department department : departments) {
			// Create new data transfer object
			DepartmentDTO dto = new DepartmentDTO();

			dto.setId(department.getId());
			dto.setName(department.getName());
			dto.setSalarymaxrange(department.getSalarymaxrange());
			dto.setSalaryminrange(department.getSalaryminrange());

			// Add to model list
			departmentsDTO.add(dto);
		}

		// Add to model
		model.addAttribute("departments", departmentsDTO);

		// This will resolve to /WEB-INF/jsp/records.jsp
		return "records";
	}

	/**
	 * Retrieves the "Add New Record" page
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAdd(Model model) {
		logger.debug("Received request to show add page");

		// Create new Department and add to model
		model.addAttribute("departmentAttribute", new Department());

		// This will resolve to /WEB-INF/jsp/add-department.jsp
		return "add-department";
	}

	/**
	 * Adds a new record for department
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postAdd(
			@Valid @ModelAttribute("departmentAttribute") Department department,
			BindingResult result, SessionStatus status) {
		logger.debug("Received request to add new record");

		if (result.hasErrors()) {
			// if validator failed
			return "add-department";
		} else {
			status.setComplete();
			departmentService.add(department);
			return "redirect:/krams/main/record/list";
		}
	}

	/**
	 * Deletes a record including all the associated employees
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("id") Integer DepartmentId) {
		logger.debug("Received request to delete record");

		// Delete Department
		departmentService.delete(DepartmentId);

		// Redirect to url
		return "redirect:/krams/main/record/list";
	}

	/**
	 * Retrieves the "Edit Existing Record" page for department
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getEdit(@RequestParam("id") Integer DepartmentId, Model model) {
		logger.debug("Received request to show edit page");

		// Retrieve Department by id
		Department existingDepartment = departmentService.get(DepartmentId);

		// Add to model
		model.addAttribute("departmentAttribute", existingDepartment);

		// This will resolve to /WEB-INF/jsp/edit-department.jsp
		return "edit-department";
	}

	/**
	 * Edits an existing record
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String postEdit(
			@Valid @ModelAttribute("departmentAttribute") Department department,
			BindingResult result, @RequestParam("id") Integer departmentId) {
		logger.debug("Received request to edit existing Department");

		if (result.hasErrors()) {
			// if validator failed
			return "edit-department";
		} else {
			// Assign id
			department.setId(departmentId);

			// Delegate to service
			departmentService.edit(department);

			// Redirect to url
			return "redirect:/krams/main/record/list";
		}

	}

}
