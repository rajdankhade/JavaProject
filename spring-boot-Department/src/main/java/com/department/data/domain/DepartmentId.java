package com.department.data.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Represents a Department entity id
 *
 * @author Rajkumar
 * @version 1.0
 * 
 */

public class DepartmentId implements Serializable {

	private static final long serialVersionUID = -5527566248002296042L;

	@Id
	@Column(name = "ID")
	private String id;

	public DepartmentId() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
