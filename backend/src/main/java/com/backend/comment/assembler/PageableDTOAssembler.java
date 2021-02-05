package com.backend.comment.assembler;


import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;

import java.util.Collection;

public class PageableDTOAssembler {

    private PageableDTOAssembler() {
    }

    public static <T> CommonDTO<PageableDTO<T>> convertToDTO(Collection<T> content, int totalElements, int totalPage) {
        PageableDTO<T> pageableDTO = new PageableDTO<>();
        pageableDTO.setContent(content);
        pageableDTO.setTotalPages(totalPage);
        pageableDTO.setTotalElements(totalElements);
        CommonDTO<PageableDTO<T>> commonDTO = new CommonDTO<>();
        commonDTO.setData(pageableDTO);
        commonDTO.setSuccess(true);
        return commonDTO;
    }
}
