package com.gmail.buckartz.roomreservation.service.reservation.impl;

import com.gmail.buckartz.roomreservation.dao.reservation.ReservationGetAllByEmployeeDao;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationGetAllByEmployeeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationGetAllByEmployeeIdServiceImpl implements ReservationGetAllByEmployeeIdService {
    @Autowired
    private ReservationGetAllByEmployeeDao getAllByEmployee;

    @Override
    public List<Reservation> findAllByParentId(Long id) {
        return getAllByEmployee.findAllByEmployeeId(id);
    }
}
