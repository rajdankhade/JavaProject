package com.department.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.department.data.domain.Department;
import com.department.data.exception.DepartmentNotFoundException;
import com.department.data.repositories.DepartmentRepository;
import com.department.data.service.DepartmentService;

/**
 * Represents a implementation for DepartmentService
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> findAll() throws DepartmentNotFoundException{
		return departmentRepository.findAll();
	}

	public void saveDepartment(Department department) {		
		departmentRepository.save(department);
	}

	public Department findOne(Integer id) throws DepartmentNotFoundException{		
		return departmentRepository.findOne(id);
	}

	public void delete(Integer id) throws DepartmentNotFoundException{
		departmentRepository.delete(id);

	}

	public List<Department> findByDepartment(String department) {
		return departmentRepository.findByName(department);
	}

	@Override
	public List<Department> findByDepartmentAndSalary(String department,
			double salary) {
		return departmentRepository
				.findByNameAndSalaryminrangeLessThanAndSalarymaxrangeGreaterThan(
						department, salary, salary);
	}

}
