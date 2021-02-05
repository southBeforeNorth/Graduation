package com.backend.comment.util;

import org.springframework.data.domain.PageRequest;

public class PageableUtils {

    private PageableUtils() {}

    public static PageRequest getPageable(int page, int pageSize, int start) {
        int newPage = (page * pageSize + start) / pageSize;
        return PageRequest.of(newPage, pageSize);
    }

}
