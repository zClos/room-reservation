package com.gmail.buckartz.roomreservation.service.employee.impl;

import com.gmail.buckartz.roomreservation.dao.employee.EmployeeRepository;
import com.gmail.buckartz.roomreservation.domain.employee.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee findOne(Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public Page<Employee> findAll(int page, int size) {
        return employeeRepository.findAll(new PageRequest(page, size));
    }

    @Override
    public boolean exists(Long id) {
        return employeeRepository.exists(id);
    }
}
