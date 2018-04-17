package com.gmail.buckartz.roomreservation.mapping.employee;

import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.mapping.DeserializeMapping;
import com.gmail.buckartz.roomreservation.mapping.employee.mapper.EmployeeMapper;

public interface EmployeeDeserializeMapping extends DeserializeMapping<Employee, EmployeeMapper> {
}
