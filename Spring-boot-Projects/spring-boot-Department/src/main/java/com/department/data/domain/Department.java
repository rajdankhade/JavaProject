package com.department.data.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents a Department entity
 *
 * @author Rajkumar
 * @version 1.0
 * 
 */

@Entity
@Table(name = "DEPARTMENT")
public class Department implements Serializable {

	private static final long serialVersionUID = -5527566248002296042L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@NotNull
	@Column(name = "NAME")
	private String name;

	@NotNull
	@Column(name = "SALARY_MIN_RANGE")
	private Double salaryminrange;

	@NotNull
	@Column(name = "SALARY_MAX_RANGE")
	private Double salarymaxrange;

	public Department() {
		super();
	}

	public Department(Integer id, String name, Double salaryminrange,
			Double salarymaxrange) {
		super();
		this.id = id;
		this.name = name;
		this.salaryminrange = salaryminrange;
		this.salarymaxrange = salarymaxrange;
	}

	public Department(String id2, String name2, String salaryminrange2,
			String salarymaxrange2) {		
	}

	public Department(String id2) {
		
	}

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

}
