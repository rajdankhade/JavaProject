package com.department.data.domain;

import java.io.Serializable;

/**
 * Represents a Department DTOEntity
 * 
 * @author  Rajkumar
 * @version 1.0
 */

public class DepartmentDTO implements Serializable {

	private static final long serialVersionUID = -5527566248002296042L;

	private Integer id;

	private String name;

	private Double salaryminrange;

	private Double salarymaxrange;

	private Integer code;

	private String statusMessage;

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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
