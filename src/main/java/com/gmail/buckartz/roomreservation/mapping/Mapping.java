package com.gmail.buckartz.roomreservation.mapping;

public interface Mapping<T, M> {
    T toObject(M from);
}
