package com.employee.data.service;

import java.util.List;
import com.employee.data.model.Department;

/**
 * Represents a Department Service
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

public interface DepartmentService {
	public List<Department> findAll();

	public void saveDepartment(Department department);

	public Department findOne(Integer id);

	public void delete(Integer id);

	public Department findByDepartment(String department);

}
