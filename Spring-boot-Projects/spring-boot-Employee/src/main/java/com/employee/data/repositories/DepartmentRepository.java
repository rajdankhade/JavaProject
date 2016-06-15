package com.employee.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.data.model.Department;

/**
 * Represents a Department Repository
 *
 * @author  Rajkumar
 * @version 1.0
 * 
 */

public interface DepartmentRepository extends
		JpaRepository<Department, Integer> {

	Department findByName(String department);

}
