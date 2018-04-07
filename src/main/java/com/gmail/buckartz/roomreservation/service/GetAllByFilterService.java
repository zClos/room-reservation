package com.gmail.buckartz.roomreservation.service;

import java.util.List;

public interface GetAllByFilterService<T, F> {
    List<T> findAllByFilter(F f);
}
