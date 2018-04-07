package com.gmail.buckartz.roomreservation.service.employee.impl;

import com.gmail.buckartz.roomreservation.dao.employee.EmployeeGetByIdDao;
import com.gmail.buckartz.roomreservation.domain.Employee;
import com.gmail.buckartz.roomreservation.service.employee.EmployeeGetByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeGetByIdServiceImpl implements EmployeeGetByIdService {
    @Autowired
    private EmployeeGetByIdDao getByIdDao;

    @Override
    public Employee findById(Long id) {
        return getByIdDao.findById(id);
    }
}
