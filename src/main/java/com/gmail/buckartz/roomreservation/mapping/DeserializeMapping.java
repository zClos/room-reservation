package com.gmail.buckartz.roomreservation.mapping;

public interface DeserializeMapping<T, M> {
    T toObject(M from);
}
