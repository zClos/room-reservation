package com.gmail.buckartz.roomreservation.mapping.content_page.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageSerializeMapper<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;

    public static PageSerializeMapperBuilder builder() {
        return new PageSerializeMapperBuilder();
    }

    public static class PageSerializeMapperBuilder {
        private PageSerializeMapper mapper = new PageSerializeMapper();

        public PageSerializeMapperBuilder content(List content) {
            mapper.setContent(content);
            return this;
        }

        public PageSerializeMapperBuilder totalElements(long totalElements) {
            mapper.setTotalElements(totalElements);
            return this;
        }

        public PageSerializeMapperBuilder totalPages(int totalPages) {
            mapper.setTotalPages(totalPages);
            return this;
        }

        public PageSerializeMapperBuilder number(int number) {
            mapper.setNumber(number + 1);
            return this;
        }

        public PageSerializeMapperBuilder size(int size) {
            mapper.setSize(size);
            return this;
        }

        public PageSerializeMapper build() {
            return mapper;
        }
    }
}
