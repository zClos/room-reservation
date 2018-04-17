package com.gmail.buckartz.roomreservation.mapping;

public interface SerializeMapping<T, F> {
    T toJson(F from);
}
