package com.gmail.buckartz.roomreservation.service;

public interface GetByIdService<T> {
    T findById(Long id);
}
