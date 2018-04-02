package com.gmail.buckartz.roomreservation.dao.employee;

import com.gmail.buckartz.roomreservation.domain.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSaveDao extends org.springframework.data.repository.Repository<Employee, Long> {
    void save(Employee employee);
}
