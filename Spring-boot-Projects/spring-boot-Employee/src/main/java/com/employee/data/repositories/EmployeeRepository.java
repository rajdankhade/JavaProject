package com.employee.data.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.data.model.Employee;

/**
 * Represents a Employee Repository
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	List<Employee> findById(Integer id);

	List<Employee> findByManagerName(String managerName);
}
