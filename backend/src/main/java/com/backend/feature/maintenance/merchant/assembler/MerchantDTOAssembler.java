package com.backend.feature.maintenance.merchant.assembler;

import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.picture.assembler.PictureDTOAssembler;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class MerchantDTOAssembler {
    private MerchantDTOAssembler (){

    }

    public static Merchant convertToEntity(MerchantDTO merchantDTO){
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO, merchant);
        if(Objects.nonNull(merchantDTO.getHeader())){
            merchant.setHeader(PictureDTOAssembler.convertToEntity(merchantDTO.getHeader()));
        }
        return merchant;
    }

    public static MerchantDTO convertToDTO(Merchant merchant){
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant, merchantDTO);
        if(Objects.nonNull(merchant.getHeader())){
            merchantDTO.setHeader(PictureDTOAssembler.convertToDTO(merchant.getHeader()));
        }
        return merchantDTO;
    }
}
