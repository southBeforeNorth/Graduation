package com.backend;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.constant.ErrorCodeConstant;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.exception.ErrorCode4xx;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCode4xx.class)
    public ResponseEntity<CommonDTO> errorCode4xx(Exception e){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonDTOAssembler.fail(ErrorCodeConstant.ERR_CODE_400, e.getMessage()));
    }
}
