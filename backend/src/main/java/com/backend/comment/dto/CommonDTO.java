package com.backend.comment.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommonDTO<T> {
    protected boolean success = true;
    protected String errorCode;
    protected List<String> errorMessage;
    protected T data;

    public interface Base {

    }
}
