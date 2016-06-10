package com.department.data.service;

import java.util.List;
import com.department.data.domain.Department;

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

	public List<Department> findByDepartment(String department);

	public List<Department> findByDepartmentAndSalary(String department,
			double salary);

}
