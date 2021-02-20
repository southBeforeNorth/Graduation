package com.backend.feature.maintenance.sportGround.assembler;

import com.backend.feature.maintenance.sportGround.dto.AreaDTO;
import com.backend.feature.maintenance.sportGround.entity.Area;
import org.springframework.beans.BeanUtils;

public class AreaDTOAssembler {
    private AreaDTOAssembler (){

    }
    public static Area convertToEntity(AreaDTO areaDTO){
        Area area = new Area();
        BeanUtils.copyProperties(areaDTO, area);
        return area;
    }

    public static AreaDTO convertToDTO(Area area){
        AreaDTO areaDTO = new AreaDTO();
        BeanUtils.copyProperties(area, areaDTO);
        return areaDTO;
    }
}
