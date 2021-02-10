package com.backend.feature.maintenance.merchant.service;

import com.backend.comment.assembler.PageableDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.feature.maintenance.merchant.assembler.MerchantDTOAssembler;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.merchant.exception.MerchantException;
import com.backend.feature.maintenance.merchant.repository.MerchantRepository;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import com.backend.feature.maintenance.user.exception.UserException;
import com.backend.util.token.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
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
    public MerchantDTO deleteMerchantById(String id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));
        merchantRepository.deleteById(id);
        return MerchantDTOAssembler.convertToDTO(merchant);
    }
    public MerchantDTO updateMerchant(String id, MerchantDTO merchantDTO){
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));
        Optional<Merchant> optionalMerchant =
                merchantRepository.getByMerchantName(merchantDTO.getMerchantName());
        if(optionalMerchant.isPresent() && !StringUtils.equals(merchant.getId(), optionalMerchant.get().getId()) ){
            throw new MerchantException(MerchantException.MERCHANT_IS_EXIST);
        }
        Merchant saveMerchant = MerchantDTOAssembler.convertToEntity(merchantDTO);
        saveMerchant.setId(id);
        Merchant updateMerchant = merchantRepository.saveAndFlush(saveMerchant);
        return MerchantDTOAssembler.convertToDTO(updateMerchant);
    }
    public CommonDTO<PageableDTO<MerchantDTO>> getMerchantListByPage (
            PageRequest pageRequest,
            String merchantName,
            String contactPerson,
            Boolean active){
        Specification<Merchant> specification = getMerchantSpecification(merchantName, contactPerson, active);
        Page<Merchant> merchantPage = merchantRepository.findAll(specification, pageRequest);
        List<MerchantDTO> merchantDTOList = merchantPage.getContent()
                .stream()
                .map(MerchantDTOAssembler::convertToDTO)
                .sorted(Comparator.comparing(MerchantDTO::getMerchantName, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return PageableDTOAssembler.convertToDTO(merchantDTOList, (int) merchantPage.getTotalElements(),merchantPage.getTotalPages());

    }

    public String login(MerchantDTO merchantDTO) {
        Optional<Merchant> merchant = merchantRepository.getByMerchantNameAndPassword(merchantDTO.getMerchantName(), merchantDTO.getPassword());
        if (merchant.isPresent() && merchant.get().isActive()) {
            return TokenUtil.sign(merchant.get().getId(), merchant.get().getMerchantName());
        } else {
            throw new MerchantException(MerchantException.MERCHANT_NO_EXIST);
        }
    }
    public MerchantDTO getMerchantById(){
        String merchantId = this.getMerchantId();
        Optional<Merchant> Merchant = merchantRepository.findById(merchantId);
        if(!Merchant.isPresent()) {
            throw new MerchantException(MerchantException.MERCHANT_NO_EXIST);
        } else {
            return MerchantDTOAssembler.convertToDTO(Merchant.get());
        }
    }

    private String getMerchantId(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request.getAttribute("id").toString();
    }

    private Specification<Merchant> getMerchantSpecification(String merchantName, String contactPerson, Boolean active) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StringUtils.isNotEmpty(merchantName)) {
                predicateList.add(cb.like(root.get("merchantName"), "%" + merchantName + "%"));
            }
            if (StringUtils.isNotEmpty(contactPerson)) {
                predicateList.add(cb.like(root.get("contactPerson"), "%" + contactPerson + "%"));
            }

            if (Objects.nonNull(active)) {
                predicateList.add(cb.equal(root.get("active"), active));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            query.where(cb.and(predicateList.toArray(predicates)));
            return query.getRestriction();
        };
    }
}
