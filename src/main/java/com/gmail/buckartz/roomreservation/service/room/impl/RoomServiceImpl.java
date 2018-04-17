package com.gmail.buckartz.roomreservation.service.room.impl;

import com.gmail.buckartz.roomreservation.dao.RoomRepository;
import com.gmail.buckartz.roomreservation.domain.Room;
import com.gmail.buckartz.roomreservation.service.room.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findOne(Long id) {
        return roomRepository.findOne(id);
    }

    @Override
    public boolean exists(Long id) {
        return roomRepository.exists(id);
    }

    @Override
    public Room findByNumber(String number) {
        return roomRepository.findByNumber(number);
    }

    @Override
    public Page<Room> findAll(int page, int size) {
        return roomRepository.findAll(new PageRequest(page, size));
    }
}
