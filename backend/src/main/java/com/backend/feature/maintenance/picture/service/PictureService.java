package com.backend.feature.maintenance.picture.service;


import com.backend.feature.maintenance.picture.assembler.PictureDTOAssembler;
import com.backend.feature.maintenance.picture.dto.PictureDTO;
import com.backend.feature.maintenance.picture.entity.Picture;
import com.backend.feature.maintenance.picture.exception.PictureException;
import com.backend.feature.maintenance.picture.repository.PictureRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
public class PictureService {

    private static final long MAX_FILE_SIZE = 1024L * 1024L * 5L;

    @Autowired
    private PictureRepository pictureRepository;


    public List<PictureDTO> getFileList(String sportGroundId) {
        List<Picture> pictures = pictureRepository.findAllBySportGroundId(sportGroundId);
        return pictures.stream().map(PictureDTOAssembler::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<PictureDTO> saveFiles(MultipartFile[] files) {
        List<Picture> pictureList = new ArrayList<>();
        for (MultipartFile file : files) {
            validateFile(file);
            Picture picture = new Picture();
            Encode.forHtml(file.getOriginalFilename());
            picture.setFileName(getFileName(file.getOriginalFilename()));
            picture.setContentType(file.getContentType());
            try {
                picture.setFileContent(file.getBytes());
                pictureList.add(picture);
            } catch (IOException e) {
                throw new PictureException(PictureException.PICTURE_NOT_EXIST);
            }
        }
        return PictureDTOAssembler.convertToDTOList(pictureRepository.saveAll(pictureList));
    }

    private String getFileName(String fileName) {
        String safeFileName = Encode.forHtml(fileName);
        int fileNameStartIndex = safeFileName.lastIndexOf('\\');
        return safeFileName.substring(fileNameStartIndex + 1);
    }

    private void validateFile(MultipartFile file) {
        if (StringUtils.isBlank(file.getOriginalFilename())) {
            throw new PictureException(PictureException.ERROR_MESSAGE_ILLEGAL_FILE);
        }

        if (!file.getOriginalFilename().toLowerCase().matches(".*\\.(gif|jpg|jpeg|tif|bmp|png)")) {
            throw new PictureException(PictureException.ERROR_MESSAGE_ILLEGAL_FILE);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new PictureException(PictureException.ERROR_MESSAGE_ILLEGAL_FILE);
        }
    }


}
