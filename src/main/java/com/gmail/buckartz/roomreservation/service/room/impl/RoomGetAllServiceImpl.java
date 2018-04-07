package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.room.RoomGetAllDao;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomGetAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class RoomGetAllServiceImpl implements RoomGetAllService {
    @Autowired
    private RoomGetAllDao getAllDao;

    @Override
    public Set<Room> findAll() {
        return getAllDao.findAll();
    }
}
