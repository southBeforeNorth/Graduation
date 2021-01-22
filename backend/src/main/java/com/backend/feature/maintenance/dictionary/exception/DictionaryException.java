package com.backend.feature.maintenance.dictionary.exception;

import com.backend.comment.exception.ErrorCode4xx;

public class DictionaryException extends ErrorCode4xx {

    public static final String DICTIONARY_NO_EXIST = "此字典不存在";
    public static final String DICTIONARY_IS_EXIST = "此字典已经存在";

    public DictionaryException(String msg) {
        super(msg);
    }
}
