package com.departmentapp.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.departmentapp.domain.Employee;
import com.departmentapp.service.EmployeeService;

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
	
	@RequestMapping(value = "/getManager", method = RequestMethod.GET)
	@ResponseBody
	public String validateManager(@RequestParam("manName") String managerName,
			Model model) {
		logger.debug("Received request to validateManager");
		Employee emp = new Employee();
		// Retrieve credit card by id
		String res = employeeService.findManager(managerName);
		return res;
	}

	@RequestMapping(value = "/getDepartment", method = RequestMethod.GET)
	@ResponseBody
	public String validateDepartment(@RequestParam("dep") String dep,
			Model model) {
		logger.debug("Received request to Department");
		Employee emp = new Employee();
		// Retrieve credit card by id
		String res = employeeService.findDepartment(dep);
		return res;
	}

	@RequestMapping(value = "/getSalary", method = RequestMethod.GET)
	@ResponseBody
	public String validateSalary(@RequestParam("dep") String dep,
			@RequestParam("sal") String sal, Model model) {
		logger.debug("Received request to Salary");

		Employee emp = new Employee();
		// Retrieve credit card by id
		String res = employeeService.findSalary(dep, sal);
		return res;
	}

}
