package com.backend.feature.maintenance.picture.exception;

import com.backend.comment.exception.ErrorCode4xx;
import lombok.Getter;

@Getter
public class PictureException extends ErrorCode4xx {

    public static final String PICTURE_NOT_EXIST = "Picture is not existed.";
    public static final String ERROR_MESSAGE_ILLEGAL_FILE = "Illegal file.";
    public static final String ERROR_MESSAGE_OTHERS = "File upload has some problem";

    public PictureException(String message) {
        super(message);
    }
}
