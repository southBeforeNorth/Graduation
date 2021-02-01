package com.backend.feature.maintenance.merchant.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.service.MerchantService;
import com.backend.feature.maintenance.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/create")
    public CommonDTO<MerchantDTO> createUser(@RequestBody MerchantDTO merchantDTO) {
        return CommonDTOAssembler.convertTODTO(merchantService.create(merchantDTO));
    }

    @GetMapping("/nameList")
    public CommonDTO<List<String>> getMerchantNameList() {
        return CommonDTOAssembler.convertTODTO(merchantService.getMerchantNameList());
    }
}
