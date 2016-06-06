package com.employeeapp.dao;

import java.util.List;
import com.employeeapp.domain.Employee;

public interface EmployeeDAO {

	public List<Employee> getAll(Integer departmentId);

	public List<Employee> getAll();

	public Employee get(Integer id);

	public void add(Integer personId, Employee employee);

	public void delete(Integer id);

	public void edit(Employee employee);

	public String findManager(String managerName);

}
