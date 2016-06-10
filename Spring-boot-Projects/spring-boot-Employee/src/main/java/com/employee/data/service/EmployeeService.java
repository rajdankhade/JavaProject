package com.employee.data.service;

import java.util.List;
import com.employee.data.model.Employee;

/**
 * Represents a Employee Service
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

public interface EmployeeService {
	public List<Employee> findAll();

	public void saveEmployee(Employee department);

	public Employee findOne(Integer id);

	public void delete(Integer id);

	public List<Employee> findById(Integer id);

	public List<Employee> findByManagerName(String managerName);

}
