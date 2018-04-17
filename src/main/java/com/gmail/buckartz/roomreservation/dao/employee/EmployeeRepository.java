package com.gmail.buckartz.roomreservation.dao.employee;

import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {
}
