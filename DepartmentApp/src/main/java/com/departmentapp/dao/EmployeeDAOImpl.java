package com.departmentapp.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.departmentapp.domain.Department;
import com.departmentapp.domain.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(EmployeeDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Retrieves all Employees
	 */
	public List<Employee> getAll(Integer departmentId) {
		logger.debug("Retrieving all employees");
		logger.debug("Department Id:" + departmentId);
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session
				.createQuery("FROM Department as d LEFT JOIN FETCH  d.employees WHERE d.id="
						+ departmentId);

		Department department = (Department) query.uniqueResult();

		// Retrieve all
		return new ArrayList<Employee>(department.getEmployees());
	}

	/**
	 * Retrieves all employee
	 */
	@SuppressWarnings("unchecked")
	public List<Employee> getAll() {
		logger.debug("Retrieving all employee");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM  Employee");

		// Retrieve all
		return query.list();
	}

	/**
	 * Retrieves a single employee
	 */
	public Employee get(Integer id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing credit card
		Employee employee = (Employee) session.get(Employee.class, id);

		// Persists to db
		return employee;
	}

	/**
	 * Adds a new employee
	 */
	public void add(Integer personId, Employee employee) {
		logger.debug("Adding new employee");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Persists to db
		session.save(employee);

		// Add to person as well
		// Retrieve existing department via id
		Department existingDepartment = (Department) session.get(
				Department.class, personId);

		// Assign updated values to this department
		existingDepartment.getEmployees().add(employee);

		// Save updates
		session.save(existingDepartment);
	}

	/**
	 * Deletes an existing employee
	 */
	public void delete(Integer id) {
		logger.debug("Deleting existing employee");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Delete reference to foreign key credit card first
		// We need a SQL query instead of HQL query here to access the third
		// table
		Query query = session.createSQLQuery("DELETE FROM DEPARTMENT_EMPLOYEE "
				+ "WHERE employees_ID=" + id);

		query.executeUpdate();

		// Retrieve existing employee
		Employee employee = (Employee) session.get(Employee.class, id);

		// Delete
		session.delete(employee);
	}

	/**
	 * Edits an existing employee
	 */
	public void edit(Employee employee) {
		logger.debug("Editing existing employee");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing employee via id
		Employee existingEmployee = (Employee) session.get(Employee.class,
				employee.getId());

		// Assign updated values to this credit card
		existingEmployee.setDepartment(employee.getDepartment());
		existingEmployee.setManagerName(employee.getManagerName());
		existingEmployee.setName(employee.getName());
		existingEmployee.setSalary(employee.getSalary());
		// Save updates
		session.save(existingEmployee);
	}

	/**
	 * Find an existing Manager
	 */
	public String findManager(String managerName) {
		logger.debug("Find Manager");
		logger.debug("ManagerName:" + managerName);

		Session session = sessionFactory.getCurrentSession();

		String res = null;
		String qry = null;

		try {
			if (!"".equals(managerName)) {
				qry = "" + "FROM Employee WHERE MANAGER_NAME = '" + managerName
						+ "'";
			}
			Query query = session.createQuery(qry);
			List list = query.list();

			if (list != null) {
				if (list.size() > 0) {
					res = "true";
				} else {
					res = "false";
				}
			}

		} catch (Exception e) {
			logger.error("Exception in findManager::EmployeeService");
		}
		return res;
	}

	/**
	 * Find an existing Department
	 */
	public String findDepartment(String dep) {

		logger.debug(" Start Service findDepartment");
		logger.debug(" Department:" + dep);

		Session session = sessionFactory.getCurrentSession();

		String res = null;
		String qry = null;
		try {
			if (!"".equals(dep)) {
				qry = "" + "FROM Department WHERE NAME = '" + dep + "'";
			}

			Query query = session.createQuery(qry);

			List list = query.list();

			logger.debug("Department size:" + list.size());
			if (list != null) {
				if (list.size() > 0) {
					res = "true";
				} else {
					res = "false";
				}
			}
		} catch (Exception e) {
			logger.error("Exception in findDepartment::EmployeeService");
		}
		return res;

	}

	/**
	 * Edits an existing Department
	 */
	public String findSalary(String dep, String sal) {

		logger.debug(" Start Service findSalary");
		logger.debug(" Department:" + dep);
		logger.debug(" Salary:" + sal);

		Session session = sessionFactory.getCurrentSession();

		String qry = null;
		String res = null;

		try {
			qry = ""
					+ "FROM Department WHERE '"
					+ sal
					+ "' between SALARY_MIN_RANGE and SALARY_MAX_RANGE AND NAME = '"
					+ dep + "'";
			Query query = session.createQuery(qry);
			List list = query.list();

			if (list != null) {
				if (list.size() > 0) {
					res = "true";

				} else {
					res = "false";

				}
			}

		} catch (Exception e) {
			logger.error("Exception in findSalary::EmployeeService");
		}
		return res;

	}

}
