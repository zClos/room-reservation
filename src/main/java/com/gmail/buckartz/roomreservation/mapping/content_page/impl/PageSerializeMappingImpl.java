package com.gmail.buckartz.roomreservation.mapping.content_page.impl;

import com.gmail.buckartz.roomreservation.mapping.content_page.PageSerializeMapping;
import com.gmail.buckartz.roomreservation.mapping.content_page.mapper.PageSerializeMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageSerializeMappingImpl<T> implements PageSerializeMapping<T> {
    @Override
    public PageSerializeMapper toJson(Page<T> from) {
        return PageSerializeMapper.builder()
                .content(from.getContent())
                .totalElements(from.getTotalElements())
                .totalPages(from.getTotalPages())
                .number(from.getNumber())
                .size(from.getSize())
                .build();
    }
}
