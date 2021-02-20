package com.backend.feature.maintenance.sportGround.exception;

import com.backend.comment.exception.ErrorCode4xx;

public class SportGroundException extends ErrorCode4xx {

    public static final String SPORT_GROUND_NO_EXIST="此运动场不存在";

    public SportGroundException(String msg){
        super(msg);
    }
}
