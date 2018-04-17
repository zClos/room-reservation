package com.gmail.buckartz.roomreservation.dao;

import com.gmail.buckartz.roomreservation.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>, PagingAndSortingRepository<Room, Long> {
    Room findByNumber(String number);
}
