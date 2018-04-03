package com.gmail.buckartz.roomreservation.service;

import java.util.Set;

public interface GetAllService<T> {
    Set<T> findAll();
}
