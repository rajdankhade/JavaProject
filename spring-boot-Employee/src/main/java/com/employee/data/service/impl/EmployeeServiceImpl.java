package com.employee.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.data.exception.EmployeeNotFoundException;
import com.employee.data.model.Employee;
import com.employee.data.repositories.EmployeeRepository;
import com.employee.data.service.EmployeeService;

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

	public List<Employee> findAll() throws EmployeeNotFoundException {
		return employeeRepository.findAll();
	}

	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findOne(Integer id) throws EmployeeNotFoundException{
		return employeeRepository.findOne(id);
	}

	public void delete(Integer id) throws EmployeeNotFoundException{
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
