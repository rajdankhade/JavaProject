package com.employeeapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MyControllerTest {

	@InjectMocks
	private DepartmentController departmentController;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
				.build();

	}

	@Test
	public void testAddRecords() throws Exception {
		this.mockMvc.perform(get("/main/record/add"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("WEB-INF/jsp/add-department.jsp"))
				.andExpect(model().attributeExists("departmentAttribute"));
	}
}
