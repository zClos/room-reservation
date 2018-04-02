package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.room.RoomGetByNumberDao;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomGetByNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomGetByNumberServiceImpl implements RoomGetByNumberService {
    @Autowired
    private RoomGetByNumberDao getByNumberDao;

    @Override
    public Room getByNumber(String number) {
        return getByNumberDao.getByNumber(number);
    }
}