package com.departmentapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.departmentapp.dao.DepartmentDAO;
import com.departmentapp.domain.Department;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDAO departmentDAO;

	@Transactional
	public Department get(Integer id) {
		return this.departmentDAO.get(id);
	}

	@Transactional
	public void add(Department department) {
		this.departmentDAO.add(department);

	}

	@Transactional
	public void edit(Department department) {
		this.departmentDAO.edit(department);

	}

	@Transactional
	public List<Department> getAll() {
		return this.departmentDAO.getAll();
	}

	@Transactional
	public void delete(Integer id) {
		this.departmentDAO.delete(id);

	}

}
