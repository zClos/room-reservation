package com.gmail.buckartz.roomreservation.service.reservation.impl;

import com.gmail.buckartz.roomreservation.dao.reservation.ReservationGetAllByEmployeeDao;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationGetAllByEmployeeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationGetAllByEmployeeIdServiceImpl implements ReservationGetAllByEmployeeIdService {
    @Autowired
    private ReservationGetAllByEmployeeDao getAllByEmployee;

    @Override
    public List<Reservation> getAllByParentId(Long id) {
        return getAllByEmployee.getAllByEmployeeId(id);
    }
}
