package com.employee.data.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.employee.data.model.Employee;
import com.employee.data.repositories.EmployeeRepository;

/**
 * Represents a implementation for EmployeeService
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findOne(Integer id) {
		return employeeRepository.findOne(id);
	}

	public void delete(Integer id) {
		employeeRepository.delete(id);

	}

	@Override
	public List<Employee> findById(Integer id) {
		return employeeRepository.findById(id);
	}

	@Override
	public List<Employee> findByManagerName(String managerName) {
		return employeeRepository.findByManagerName(managerName);
	}

}
