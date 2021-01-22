package com.backend.feature.maintenance.user.exception;

import com.backend.comment.exception.ErrorCode4xx;

public class UserException extends ErrorCode4xx {

    public static final String USER_NO_EXIST = "此用户不存在";
    public static final String USER_IS_EXIST = "此用户已经存在";

    public UserException(String msg) {
        super(msg);
    }
}
