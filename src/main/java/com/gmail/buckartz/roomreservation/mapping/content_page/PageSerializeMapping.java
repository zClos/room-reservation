package com.gmail.buckartz.roomreservation.mapping.content_page;

import com.gmail.buckartz.roomreservation.mapping.SerializeMapping;
import com.gmail.buckartz.roomreservation.mapping.content_page.mapper.PageSerializeMapper;
import org.springframework.data.domain.Page;

public interface PageSerializeMapping<T> extends SerializeMapping<PageSerializeMapper, Page<T>> {
}
