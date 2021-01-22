package com.backend.comment.assembler;

import com.backend.comment.dto.CommonDTO;

import java.util.Collections;

public class CommonDTOAssembler {
    private CommonDTOAssembler() {
    }

    public static <T> CommonDTO<T> convertTODTO(T data) {
        CommonDTO<T> commonDTO = new CommonDTO<>();
        commonDTO.setData(data);
        return commonDTO;
    }

    public static <T> CommonDTO<T> fail(String errorCode, String errorMsg) {
        CommonDTO<T> commonDTO = new CommonDTO<>();
        commonDTO.setSuccess(false);
        commonDTO.setErrorCode(errorCode);
        commonDTO.setErrorMessage(Collections.singletonList(errorMsg));
        return commonDTO;
    }
}
