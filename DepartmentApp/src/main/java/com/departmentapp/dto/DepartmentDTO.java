package com.departmentapp.dto;

import java.util.List;
import com.departmentapp.domain.Employee;

/**
 * Data Transfer Object for displaying purposes
 * 
 * @author Rajkumar
 */
public class DepartmentDTO {

	private Integer id;
	private String name;
	private Double salaryminrange;
	private Double salarymaxrange;
	private List<Employee> employees;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalaryminrange() {
		return salaryminrange;
	}

	public void setSalaryminrange(Double salaryminrange) {
		this.salaryminrange = salaryminrange;
	}

	public Double getSalarymaxrange() {
		return salarymaxrange;
	}

	public void setSalarymaxrange(Double salarymaxrange) {
		this.salarymaxrange = salarymaxrange;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
