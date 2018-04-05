package com.gmail.buckartz.roomreservation.service.reservation.impl;

import com.gmail.buckartz.roomreservation.dao.reservation.ReservationGetAllByRoomAndPeriodDao;
import com.gmail.buckartz.roomreservation.service.reservation.ReservationGetCountByRoomAndPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReservationGetCountByRoomAndPeriodServiceImpl implements ReservationGetCountByRoomAndPeriodService {
    @Autowired
    private ReservationGetAllByRoomAndPeriodDao byRoomAndPeriodDao;

    @Override
    public int execute(Long id, Timestamp from, Timestamp to) {
        return byRoomAndPeriodDao.getAllByRoomIdAndPeriod(id, from, to);
    }
}
