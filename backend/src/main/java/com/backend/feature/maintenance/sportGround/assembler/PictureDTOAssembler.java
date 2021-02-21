package com.backend.feature.maintenance.sportGround.assembler;

import com.backend.feature.maintenance.sportGround.dto.PictureDTO;
import com.backend.feature.maintenance.sportGround.entity.Picture;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PictureDTOAssembler {

    private PictureDTOAssembler() {
    }

    public static List<Picture> convertToEntityList(List<PictureDTO> pictureDTOList) {
        return pictureDTOList.stream()
                .map(PictureDTOAssembler::convertToEntity)
                .collect(Collectors.toList());
    }

    public static List<PictureDTO> convertToDTOList(List<Picture> pictureList) {
        return pictureList.stream()
                .map(PictureDTOAssembler::convertToDTO)
                .collect(Collectors.toList());
    }

    public static PictureDTO convertToDTO(Picture picture) {
        PictureDTO pictureDTO = new PictureDTO();
        BeanUtils.copyProperties(picture, pictureDTO);
        return pictureDTO;
    }

    public static Picture convertToEntity(PictureDTO pictureDTO) {
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureDTO, picture);
        return picture;
    }
}
