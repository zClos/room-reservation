package com.gmail.buckartz.roomreservation.service.employee.impl;

import com.gmail.buckartz.roomreservation.dao.employee.EmployeeSaveDao;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeSaveServiceImpl implements EmployeeSaveService {
    @Autowired
    private EmployeeSaveDao employeeSaveDao;

    @Override
    public void save(Employee employee) {
        employeeSaveDao.save(employee);
    }
}
