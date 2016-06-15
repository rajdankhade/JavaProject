package com.employee.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.employee.data.model.Employee;
import com.employee.data.service.EmployeeService;
import com.employee.data.web.EmployeeController;

/**
 * Mockito Junit test case for Employee application
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {
	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeService employeeServiceMock;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetEmployee() throws Exception {
		List<Employee> expResult = new ArrayList();
		List<Employee> inpResult = new ArrayList();
		Employee emp = new Employee();
		emp.setId(1);
		emp.setManagerName("Ajay");
		emp.setName("Science");
		emp.setSalary(300.7);
		inpResult.add(emp);
		expResult.add(emp);
		when(employeeServiceMock.findAll()).thenReturn(inpResult);
		ResponseEntity<List<Employee>> result = employeeController.getEmployees();
		assertEquals(HttpStatus.OK,result.getStatusCode());
	}	
}
