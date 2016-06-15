package com.employee.data.model;

/**
 * Employee Data Transfer Object for displaying/response purposes
 * 
 * @author Rajkumar
 */
public class EmployeeDTO {

	private Integer id;
	private String name;
	private String managername;
	private String department;
	private Double salary;
	private Double salaryminrange;
	private Double salarymaxrange;

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

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
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

}
