package com.gmail.buckartz.roomreservation.service.reservation.impl;

import com.gmail.buckartz.roomreservation.dao.reservation.ReservationSaveDao;
import com.gmail.buckartz.roomreservation.domain.Reservation;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationSaveServiceImpl implements ReservationSaveService {
    @Autowired
    private ReservationSaveDao saveDao;

    @Override
    public void save(Reservation reservation) {
        saveDao.save(reservation);
    }
}
