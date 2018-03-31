package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.room.RoomSaveDao;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoomSaveServiceImpl implements RoomSaveService {
    @Autowired
    private RoomSaveDao roomSaveDao;

    @Override
    public void save(Room room) {
        roomSaveDao.save(room);
    }
}
