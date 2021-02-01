package com.backend.feature.maintenance.merchant.assembler;

import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import org.springframework.beans.BeanUtils;

public class MerchantDTOAssembler {
    private MerchantDTOAssembler (){

    }

    public static Merchant convertToEntity(MerchantDTO merchantDTO){
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO, merchant);
        return merchant;
    }

    public static MerchantDTO convertToDTO(Merchant merchant){
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant, merchantDTO);
        return merchantDTO;
    }
}
