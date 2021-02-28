package com.backend.feature.maintenance.merchant.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.PageableUtils;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping("/page")
    public CommonDTO<PageableDTO<MerchantDTO>> getMerchantListByPage(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam int start,
            @RequestParam(required = false) String merchantName,
            @RequestParam(required = false) String contactPerson,
            @RequestParam(required = false) Boolean active
    ) {
        PageRequest pageRequest = PageableUtils.getPageable(page, pageSize, start);
        return merchantService.getMerchantListByPage(pageRequest, merchantName, contactPerson, active);

    }

    @PostMapping("/create")
    public CommonDTO<MerchantDTO> createMerchant(@RequestBody MerchantDTO merchantDTO) {
        return CommonDTOAssembler.convertTODTO(merchantService.create(merchantDTO));
    }

    @PostMapping("/createByManager")
    public CommonDTO<MerchantDTO> creatMerchantByManager(@RequestBody MerchantDTO merchantDTO) {
        return CommonDTOAssembler.convertTODTO(merchantService.create(merchantDTO));
    }

    @PostMapping("/login")
    public CommonDTO<String> login(@RequestBody MerchantDTO merchantDTO) {
        return CommonDTOAssembler.convertTODTO(merchantService.login(merchantDTO));
    }

    @PutMapping("/update/{id}")
    public CommonDTO<MerchantDTO> updateMerchant(@PathVariable String id, @RequestBody MerchantDTO merchantDTO) {
        return CommonDTOAssembler.convertTODTO(merchantService.updateMerchant(id, merchantDTO));
    }

    @DeleteMapping("/delete/{id}")
    public CommonDTO<MerchantDTO> delete(@PathVariable String id) {
        return CommonDTOAssembler.convertTODTO(merchantService.deleteMerchantById(id));
    }

    @GetMapping("/nameList")
    public CommonDTO<List<String>> getMerchantNameList() {
        return CommonDTOAssembler.convertTODTO(merchantService.getMerchantNameList());
    }

    @GetMapping("/merchantId")
    public CommonDTO<MerchantDTO> getMerchantId() {
        return CommonDTOAssembler.convertTODTO(merchantService.getMerchantById());
    }

    @PutMapping("/updateByOwner")
    public CommonDTO<MerchantDTO> updateMerchantByOwner(@RequestBody MerchantDTO merchantDTO) {
        return CommonDTOAssembler.convertTODTO(merchantService.updateMerchantByOwner(merchantDTO));
    }

    @GetMapping("/changePassword")
    public CommonDTO<MerchantDTO> changeMerchantPasswordByOwner(@RequestParam String newPassword,
                                                                @RequestParam String oldPassword) {
        return CommonDTOAssembler.convertTODTO(merchantService.changeMerchantPassword(oldPassword, newPassword));
    }
}
