package com.gmail.buckartz.roomreservation.service;

import java.util.List;

public interface GetAllByParentIdService<T> {
    List<T> findAllByParentId(Long id);
}
