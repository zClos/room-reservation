package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.room.RoomGetByIdDao;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomGetByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoomGetByIdServiceImpl implements RoomGetByIdService {
    @Autowired
    private RoomGetByIdDao getByIdDao;

    @Override
    public Room findById(Long id) {
        return getByIdDao.findById(id);
    }
}
