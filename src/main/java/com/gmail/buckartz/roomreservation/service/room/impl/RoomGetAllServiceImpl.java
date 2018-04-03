package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.room.RoomGetAllDao;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomGetAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoomGetAllServiceImpl implements RoomGetAllService {
    @Autowired
    private RoomGetAllDao roomGetAllDao;

    @Override
    public Set<Room> findAll() {
        return roomGetAllDao.findAll();
    }
}
