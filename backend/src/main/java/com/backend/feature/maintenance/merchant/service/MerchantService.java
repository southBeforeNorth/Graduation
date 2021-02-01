package com.backend.feature.maintenance.merchant.service;

import com.backend.feature.maintenance.merchant.assembler.MerchantDTOAssembler;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.merchant.exception.MerchantException;
import com.backend.feature.maintenance.merchant.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public MerchantDTO create(MerchantDTO merchantDTO){
        Merchant merchant = MerchantDTOAssembler.convertToEntity(merchantDTO);
        if(merchantRepository.getByMerchantName(merchant.getMerchantName()).isPresent()){
            throw new MerchantException(MerchantException.MERCHANT_IS_EXIST);
        }
        return MerchantDTOAssembler.convertToDTO(merchantRepository.save(merchant));
    }

    public List<String> getMerchantNameList() {
        return merchantRepository.findAll().stream()
                .map(Merchant::getMerchantName)
                .collect(Collectors.toList());
    }
}
