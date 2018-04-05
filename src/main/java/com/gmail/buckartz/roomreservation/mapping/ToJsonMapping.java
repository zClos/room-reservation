package com.gmail.buckartz.roomreservation.mapping;

public interface ToJsonMapping<T, F> {
    T toJson(F from);
}
