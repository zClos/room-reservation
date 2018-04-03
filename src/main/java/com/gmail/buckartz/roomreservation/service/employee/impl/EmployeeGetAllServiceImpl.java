package com.gmail.buckartz.roomreservation.service.employee.impl;

import com.gmail.buckartz.roomreservation.dao.employee.EmployeeGetAllDao;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeGetAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EmployeeGetAllServiceImpl implements EmployeeGetAllService {
    @Autowired
    private EmployeeGetAllDao getAll;

    @Override
    public Set<Employee> findAll() {
        return getAll.findAll();
    }
}
