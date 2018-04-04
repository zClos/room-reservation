package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.room.RoomGetByIdDao;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomGetByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomGetByIdServiceImpl implements RoomGetByIdService {
    @Autowired
    private RoomGetByIdDao roomGetByIdDao;

    @Override
    public Room getById(Long id) {
        return roomGetByIdDao.getById(id);
    }
}
