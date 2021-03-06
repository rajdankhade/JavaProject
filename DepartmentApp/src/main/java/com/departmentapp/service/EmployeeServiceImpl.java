package com.departmentapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.departmentapp.dao.EmployeeDAO;
import com.departmentapp.domain.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;

	@Transactional
	public List<Employee> getAll(Integer departmentId) {
		return this.employeeDAO.getAll(departmentId);
	}

	@Transactional
	public List<Employee> getAll() {
		return this.employeeDAO.getAll();
	}

	@Transactional
	public Employee get(Integer id) {
		return this.employeeDAO.get(id);
	}

	@Transactional
	public void add(Integer personId, Employee employee) {
		this.employeeDAO.add(personId, employee);

	}

	@Transactional
	public void delete(Integer id) {
		this.employeeDAO.delete(id);

	}

	@Transactional
	public void edit(Employee employee) {
		this.employeeDAO.edit(employee);

	}

	@Transactional
	public String findManager(String managerName) {
		return this.employeeDAO.findManager(managerName);
	}

	@Transactional
	public String findDepartment(String dep) {
		return this.employeeDAO.findDepartment(dep);
	}

	@Transactional
	public String findSalary(String dep, String sal) {
		return this.employeeDAO.findSalary(dep, sal);
	}

}
