package com.employeeapp.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.employeeapp.domain.Department;
import com.employeeapp.domain.Employee;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(DepartmentDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Retrieves all Departments
	 * 
	 * @return a list of Departments
	 */
	public List<Department> getAll() {
		logger.debug("Retrieving all Departments");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Department");

		// Retrieve all
		return query.list();
	}

	/**
	 * Retrieves a single Department
	 */
	public Department get(Integer id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing Department
		// Create a Hibernate query (HQL)
		Query query = session
				.createQuery("FROM Department as d LEFT JOIN FETCH  d.employees WHERE d.id="
						+ id);

		return (Department) query.uniqueResult();
	}

	/**
	 * Adds a new Department
	 */
	public void add(Department department) {
		logger.debug("Adding new Department");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Persists to db
		session.save(department);
	}

	/**
	 * Deletes an existing Department
	 * 
	 * @param id
	 *            the id of the existing Department
	 */
	public void delete(Integer id) {
		logger.debug("Deleting existing Department");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session
				.createQuery("FROM Department as p LEFT JOIN FETCH  p.employees WHERE p.id="
						+ id);

		// Retrieve record
		Department department = (Department) query.uniqueResult();

		Set<Employee> creditCards = department.getEmployees();

		// Delete Department
		session.delete(department);

		// Delete associated Employees
		for (Employee creditCard : creditCards) {
			session.delete(creditCard);
		}
	}

	/**
	 * Edits an existing Department
	 */
	public void edit(Department department) {
		logger.debug("Editing existing Department");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing Department via id
		Department existingDepartment = (Department) session.get(
				Department.class, department.getId());

		// Assign updated values to this Department
		existingDepartment.setName(department.getName());
		existingDepartment.setSalarymaxrange(department.getSalarymaxrange());
		existingDepartment.setSalaryminrange(department.getSalaryminrange());

		// Save updates
		session.save(existingDepartment);
	}
}
