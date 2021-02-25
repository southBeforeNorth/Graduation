package com.backend.feature.maintenance.order.exception;

import com.backend.comment.exception.ErrorCode4xx;
import lombok.Getter;

@Getter
public class OrderInfoException extends ErrorCode4xx {

    public static final String ORDER_INFO_NO_EXIST="此订单不存在";

    public OrderInfoException(String msg){
        super(msg);
    }
}
