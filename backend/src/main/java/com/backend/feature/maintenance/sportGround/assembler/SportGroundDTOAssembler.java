package com.backend.feature.maintenance.sportGround.assembler;

import com.backend.feature.maintenance.dictionary.assembler.DictionaryOptionAssembler;
import com.backend.feature.maintenance.dictionary.dto.DictionaryOptionDTO;
import com.backend.feature.maintenance.sportGround.dto.AreaDTO;
import com.backend.feature.maintenance.sportGround.dto.SportGroundDTO;
import com.backend.feature.maintenance.sportGround.entity.Area;
import com.backend.feature.maintenance.sportGround.entity.SportGround;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SportGroundDTOAssembler {
    private SportGroundDTOAssembler() {

    }

    public static SportGround convertToEntity(SportGroundDTO sportGroundDTO) {
        SportGround sportGround = new SportGround();
        BeanUtils.copyProperties(sportGroundDTO, sportGround);
        if(Objects.nonNull(sportGroundDTO.getWeeks())){
            sportGround.setWeeks(sportGroundDTO.getWeeks());
        }

        if(Objects.nonNull(sportGroundDTO.getTimeArea())){
            sportGround.setTimeArea(sportGroundDTO.getTimeArea());
        }

        if (Objects.nonNull(sportGroundDTO.getAreas())) {
            List<Area> areas = new ArrayList<>();
            sportGroundDTO.getAreas().forEach((value) -> areas.add(AreaDTOAssembler.convertToEntity(value)));
            areas.sort(Comparator.comparing(Area::getSequence));
            sportGround.setAreas(areas);
        }
        return sportGround;
    }

    public static SportGroundDTO convertToDTO(SportGround sportGround) {
        SportGroundDTO sportGroundDTO = new SportGroundDTO();
        BeanUtils.copyProperties(sportGround, sportGroundDTO);
        if(Objects.nonNull(sportGround.getWeeks())){
            sportGroundDTO.setWeeks(sportGround.getWeeks());
        }
        if(Objects.nonNull(sportGround.getTimeArea())){
            sportGroundDTO.setTimeArea(sportGround.getTimeArea());
        }

        if (Objects.nonNull(sportGround.getAreas())) {
            List<AreaDTO> areas = new ArrayList<>();
            sportGround.getAreas().forEach((value) -> areas.add(AreaDTOAssembler.convertToDTO(value)));
            areas.sort(Comparator.comparing(AreaDTO::getSequence));
            sportGroundDTO.setAreas(areas);
        }

        return sportGroundDTO;
    }
}
