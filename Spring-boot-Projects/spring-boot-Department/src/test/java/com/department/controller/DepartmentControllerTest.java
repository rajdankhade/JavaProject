package com.department.controller;

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

import com.department.data.domain.Department;
import com.department.data.service.DepartmentService;
import com.department.data.web.DepartmentController;

/**
 * Mockito Junit test case for Department application
 *
 * @author Rajkumar
 * @version 1.0
 * 
 */

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerTest {

	@InjectMocks
	private DepartmentController departmentController;

	@Mock
	private DepartmentService employeeServiceMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetDepartment() throws Exception {
		List<Department> expResult = new ArrayList();
		List<Department> inpResult = new ArrayList();
		Department dep = new Department();
		dep.setId(1);
		dep.setName("Ajay");
		dep.setSalaryminrange(1.0);
		dep.setSalaryminrange(1000.0);
		inpResult.add(dep);
		expResult.add(dep);
		when(employeeServiceMock.findAll()).thenReturn(inpResult);
		ResponseEntity<List<Department>> result = departmentController
				.getDepartments();
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}	
}
