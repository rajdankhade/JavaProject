package com.employee.data.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.employee.data.model.Department;
import com.employee.data.repositories.DepartmentRepository;

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

	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	public void saveDepartment(Department department) {

		departmentRepository.save(department);
	}

	public Department findOne(Integer id) {
		return departmentRepository.findOne(id);
	}

	public void delete(Integer id) {
		departmentRepository.delete(id);

	}

	@Override
	public Department findByDepartment(String department) {
		// TODO Auto-generated method stub
		return departmentRepository.findByName(department);
	}

}
