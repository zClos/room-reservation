package com.gmail.buckartz.roomreservation.service;

public interface GetByNumberService<T, V> {
    T findByNumber(V v);
}
