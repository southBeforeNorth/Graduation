package com.backend.feature.maintenance.notice.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.feature.maintenance.notice.dto.NoticeDTO;
import com.backend.feature.maintenance.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notice")
public class noticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/update")
    public CommonDTO<NoticeDTO> createSportGround(@RequestBody NoticeDTO noticeDTO) {
        return CommonDTOAssembler.convertTODTO(noticeService.create(noticeDTO));
    }

    @GetMapping("/get")
    public CommonDTO<NoticeDTO> getNoticeDTO() {
        return CommonDTOAssembler.convertTODTO(noticeService.getNoticeDTO());
    }
}
