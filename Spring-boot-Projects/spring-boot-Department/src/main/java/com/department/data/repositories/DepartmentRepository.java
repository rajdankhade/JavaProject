package com.department.data.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.department.data.domain.Department;

/**
 * Represents a Department Repository
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

public interface DepartmentRepository extends
		JpaRepository<Department, Integer> {
	List<Department> findByName(String department);

	List<Department> findByNameAndSalaryminrangeLessThanAndSalarymaxrangeGreaterThan(
			String department, double salary, double salary1);

}
