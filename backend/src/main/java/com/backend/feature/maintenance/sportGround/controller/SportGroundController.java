package com.backend.feature.maintenance.sportGround.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.PageableUtils;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.sportGround.dto.SportGroundDTO;
import com.backend.feature.maintenance.sportGround.service.SportGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sportGround")
public class SportGroundController {

    @Autowired
    private SportGroundService sportGroundService;


    @PostMapping("/create")
    public CommonDTO<SportGroundDTO> createSportGround(@RequestBody SportGroundDTO sportGroundDTO) {
        return CommonDTOAssembler.convertTODTO(sportGroundService.create(sportGroundDTO));
    }

    @GetMapping("/pageById")
    public CommonDTO<PageableDTO<SportGroundDTO>> getSportGroundsById(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam int start,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city
    ) {
        PageRequest pageRequest = PageableUtils.getPageable(page, pageSize, start);
        return sportGroundService.getSportGroundListById(pageRequest, name, city);
    }

    @GetMapping("/page")
    public CommonDTO<PageableDTO<SportGroundDTO>> getSportGroundList(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam int start,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city
    ) {
        PageRequest pageRequest = PageableUtils.getPageable(page, pageSize, start);
        return sportGroundService.getSportGroundList(pageRequest, name, city);
    }

    @DeleteMapping("/delete/{id}")
    public CommonDTO<SportGroundDTO> delete(@PathVariable String id) {
        return CommonDTOAssembler.convertTODTO(sportGroundService.delete(id));
    }

    @PutMapping("/update/{id}")
    public CommonDTO<SportGroundDTO> update(@PathVariable String id, @RequestBody SportGroundDTO sportGroundDTO) {
        return CommonDTOAssembler.convertTODTO(sportGroundService.update(id, sportGroundDTO));
    }
}
