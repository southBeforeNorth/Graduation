package com.backend.feature.maintenance.sportGround.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.feature.maintenance.sportGround.dto.PictureDTO;
import com.backend.feature.maintenance.sportGround.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/sportGround/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping
    public CommonDTO<List<PictureDTO>> getFileList(@RequestParam String id) {
        return CommonDTOAssembler.convertTODTO(pictureService.getFileList(id));
    }

    @PostMapping
    public CommonDTO<List<PictureDTO>> uploadFile(@RequestParam("files") MultipartFile[] files) {
        return CommonDTOAssembler.convertTODTO(pictureService.saveFiles(files));
    }


}
