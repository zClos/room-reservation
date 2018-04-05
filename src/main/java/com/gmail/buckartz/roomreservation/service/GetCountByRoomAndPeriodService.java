package com.gmail.buckartz.roomreservation.service;

import java.sql.Timestamp;

public interface GetCountByRoomAndPeriodService<T> {
    int execute(Long id, Timestamp from, Timestamp to);
}
