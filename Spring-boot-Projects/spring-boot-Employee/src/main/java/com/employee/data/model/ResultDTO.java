package com.employee.data.model;

/**
 * Data Transfer Object for displaying/response purposes
 * 
 * @author Rajkumar
 */

public class ResultDTO {

	private long id;
	private String description;

	public ResultDTO(long id, String description) {
		this.id = id;
		this.description = description;
	}

	public ResultDTO() {
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TaskDTO{" + "id=" + id + ", description='" + description + '\''
				+ '}';
	}
}
