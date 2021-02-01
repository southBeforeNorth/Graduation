package com.backend.feature.maintenance.merchant.exception;

import com.backend.comment.exception.ErrorCode4xx;

public class MerchantException extends ErrorCode4xx {

    public static final String MERCHANT_NO_EXIST = "此用户不存在";
    public static final String MERCHANT_IS_EXIST = "此用户已经存在";

    public MerchantException(String msg){
        super(msg);
    }

}
