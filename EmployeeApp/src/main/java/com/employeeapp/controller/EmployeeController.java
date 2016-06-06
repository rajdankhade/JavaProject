package com.employeeapp.controller;

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
import org.springframework.web.client.RestTemplate;

import com.employeeapp.domain.Employee;
import com.employeeapp.service.EmployeeService;

/**
 * Handles Employee requests
 * 
 * @author Rajkumar
 */
@Controller
@RequestMapping("/main/employee")
public class EmployeeController {

	protected static Logger logger = Logger.getLogger("EmployeeController");

	@Autowired
	private EmployeeService employeeService;

	RestTemplate rt = new RestTemplate();

	/**
	 * Retrieves the "Add New Employee" page
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAdd(@RequestParam("id") Integer departmentId, Model model) {
		logger.debug("Received request to show add page");

		// Prepare model object
		Employee employee = new Employee();

		// Add to model
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("employeeAttribute", employee);

		// This will resolve to /WEB-INF/jsp/add-employee.jsp
		return "add-employee";
	}

	/**
	 * Adds a new Employee
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postAdd(@RequestParam("id") Integer departmentId,
			@Valid @ModelAttribute("employeeAttribute") Employee employee,
			BindingResult result, Model model) {
		logger.debug("Received request to add new Employee");
		if (result.hasErrors()) {
			// if validator failed
			return "add-employee";
		} else {

			/*
			 * String manager =
			 * employeeService.findManager(employee.getManagerName());
			 * if(manager.equalsIgnoreCase("false")){
			 * logger.debug("ManagerIf cond@@@@@@@@@@");
			 * model.addAttribute("error", "Please enter valid manager");
			 * 
			 * return "add-employee"; }
			 */

			String depName = rt.getForObject(
					"http://localhost:8080/DepartmentApp/krams/main/employee/getDepartment?dep="
							+ employee.getDepartment(), String.class);
			if (depName.equalsIgnoreCase("false")) {
				model.addAttribute("error", "Please enter valid department");
				return "add-employee";
			} else {
				String sal = rt.getForObject(
						"http://localhost:8080/DepartmentApp/krams/main/employee/getSalary?dep="
								+ employee.getDepartment() + "&sal="
								+ employee.getSalary(), String.class);

				if (sal.equalsIgnoreCase("false")) {
					model.addAttribute("error", "Please enter valid salary");
					return "add-employee";
				}

				// Delegate to service
				employeeService.add(departmentId, employee);

				// Redirect to url
				return "redirect:/krams/main/record/list";
			}
		}
	}

	/**
	 * Deletes a Employee
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("id") Integer employeeId) {
		logger.debug("Received request to delete Employee");

		// Delegate to service
		employeeService.delete(employeeId);

		// Redirect to url
		return "redirect:/krams/main/record/list";
	}

	/**
	 * Retrieves the "Edit Existing Employee" page
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getEdit(@RequestParam("pid") Integer departmentId,
			@RequestParam("cid") Integer employeeId, Model model) {
		logger.debug("Received request to show edit page");

		// Retrieve employee by id
		Employee existingEmployee = employeeService.get(employeeId);

		// Add to model
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("employeeAttribute", existingEmployee);

		// This will resolve to /WEB-INF/jsp/edit-employee.jsp
		return "edit-employee";
	}

	/**
	 * Edits an existing Employee
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String postEdit(
			@Valid @ModelAttribute("employeeAttribute") Employee employee,
			BindingResult result, @RequestParam("id") Integer employeeId,
			Model model) {
		logger.debug("Received request to edit Employee");

		if (result.hasErrors()) {
			// if validator failed
			return "edit-employee";
		} else {

			// try{
			/*
			 * String res=rt.getForObject(
			 * "http://localhost:8080/ApplicationEmpDep/krams/main/department/getManager?manName="
			 * +employee.getManagerName(), String.class);
			 * logger.debug("@@@@res :"+res); if(res.equalsIgnoreCase("false")){
			 * logger.debug("if cond@@@@@@@@@@"); return "add-employee"; }
			 */

			String manager = employeeService.findManager(employee
					.getManagerName());
			if (manager.equalsIgnoreCase("false")) {
				logger.debug("ManagerIf cond@@@@@@@@@@");
				model.addAttribute("error", "Please enter valid manager");

				return "add-employee";
			}

			String depName = rt.getForObject(
					"http://localhost:8080/DepartmentApp/krams/main/employee/getDepartment?dep="
							+ employee.getDepartment(), String.class);

			if (depName.equalsIgnoreCase("false")) {
				model.addAttribute("error", "Please enter valid department");
				return "edit-employee";
			} else {
				String sal = rt.getForObject(
						"http://localhost:8080/DepartmentApp/krams/main/employee/getSalary?dep="
								+ employee.getDepartment() + "&sal="
								+ employee.getSalary(), String.class);

				if (sal.equalsIgnoreCase("false")) {
					model.addAttribute("error", "Please enter valid salary");
					return "edit-employee";
				}
				// Assign id
				employee.setId(employeeId);

				// Delegate to service
				employeeService.edit(employee);

				// Redirect to url
				return "redirect:/krams/main/record/list";
			}
		}

	}
}
