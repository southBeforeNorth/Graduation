package com.backend.comment.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO<T> {
    private Integer totalElements;
    private Integer totalPages;
    private Collection<T> content;
}
