package com.gmail.buckartz.roomreservation.dao.employee;

import com.gmail.buckartz.roomreservation.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeGetAllDao extends org.springframework.data.repository.Repository<Employee, Long> {
    @Query("SELECT e FROM Employee e")
    Set<Employee> findAll();
}
