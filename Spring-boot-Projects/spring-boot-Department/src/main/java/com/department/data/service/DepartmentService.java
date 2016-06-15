package com.department.data.service;

import java.util.List;

import com.department.data.domain.Department;
import com.department.data.exception.DepartmentNotFoundException;

/**
 * Represents a Department Service
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

public interface DepartmentService {
	public List<Department> findAll()throws DepartmentNotFoundException;

	public void saveDepartment(Department department);

	public Department findOne(Integer id)throws DepartmentNotFoundException;

	public void delete(Integer id)throws DepartmentNotFoundException;

	public List<Department> findByDepartment(String department);

	public List<Department> findByDepartmentAndSalary(String department,
			double salary);

}
