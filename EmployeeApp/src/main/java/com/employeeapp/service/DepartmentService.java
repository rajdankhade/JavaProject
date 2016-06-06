package com.employeeapp.service;

import java.util.List;
import com.employeeapp.domain.Department;

public interface DepartmentService {

	public List<Department> getAll();

	public Department get(Integer id);

	public void add(Department department);

	public void delete(Integer id);

	public void edit(Department department);

}
