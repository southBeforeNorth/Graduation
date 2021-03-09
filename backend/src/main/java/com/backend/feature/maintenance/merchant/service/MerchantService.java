package com.backend.feature.maintenance.merchant.service;

import com.backend.comment.assembler.PageableDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.UserUtils;
import com.backend.feature.maintenance.merchant.assembler.MerchantDTOAssembler;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.merchant.exception.MerchantException;
import com.backend.feature.maintenance.merchant.repository.MerchantRepository;
import com.backend.feature.maintenance.picture.entity.Picture;
import com.backend.feature.maintenance.picture.exception.PictureException;
import com.backend.feature.maintenance.picture.repository.PictureRepository;
import com.backend.util.token.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PictureRepository pictureRepository;

    public MerchantDTO create(MerchantDTO merchantDTO) {
        Merchant merchant = MerchantDTOAssembler.convertToEntity(merchantDTO);
        if (merchantRepository.getByMerchantName(merchant.getMerchantName()).isPresent()) {
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

    public MerchantDTO updateMerchant(String id, MerchantDTO merchantDTO) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));
        Optional<Merchant> optionalMerchant =
                merchantRepository.getByMerchantName(merchantDTO.getMerchantName());
        if (optionalMerchant.isPresent() && !StringUtils.equals(merchant.getId(), optionalMerchant.get().getId())) {
            throw new MerchantException(MerchantException.MERCHANT_IS_EXIST);
        }
        Merchant saveMerchant = MerchantDTOAssembler.convertToEntity(merchantDTO);
        saveMerchant.setId(id);
        saveMerchant.setType(merchant.getType());
        saveMerchant.setHeader(merchant.getHeader());
        Merchant updateMerchant = merchantRepository.saveAndFlush(saveMerchant);
        return MerchantDTOAssembler.convertToDTO(updateMerchant);
    }

    public MerchantDTO updateMerchantByOwner(MerchantDTO merchantDTO) {
        Merchant merchant = merchantRepository.findById(merchantDTO.getId())
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));
        merchant.setMerchantName(merchantDTO.getMerchantName());
        merchant.setContactPerson(merchantDTO.getContactPerson());
        merchant.setDetailedAddress(merchantDTO.getDetailedAddress());
        merchant.setPhone(merchantDTO.getPhone());
        merchant.setArea(merchantDTO.getArea());
        return MerchantDTOAssembler.convertToDTO(merchantRepository.saveAndFlush(merchant));
    }

    public MerchantDTO uploadMerchantHeaderById(String id) {
        Picture header = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureException(PictureException.PICTURE_NOT_EXIST));
        Merchant merchant = merchantRepository.findById(UserUtils.getUserId())
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));
        if (Objects.nonNull(merchant.getHeader())) {
            pictureRepository.deleteById(merchant.getHeader().getId());
        }
        merchant.setHeader(header);
        return MerchantDTOAssembler.convertToDTO(merchantRepository.saveAndFlush(merchant));
    }

    public MerchantDTO changeMerchantPassword(String oldPassword, String newPassword) {

        String merchantId = UserUtils.getUserId();
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));
        if (StringUtils.equals(merchant.getPassword(), oldPassword)) {
            merchant.setPassword(newPassword);
            return MerchantDTOAssembler.convertToDTO(merchantRepository.saveAndFlush(merchant));
        }
        return new MerchantDTO();
    }

    public CommonDTO<PageableDTO<MerchantDTO>> getMerchantListByPage(
            PageRequest pageRequest,
            String merchantName,
            String contactPerson,
            Boolean active) {
        Specification<Merchant> specification = getMerchantSpecification(merchantName, contactPerson, active);
        Page<Merchant> merchantPage = merchantRepository.findAll(specification, pageRequest);
        List<MerchantDTO> merchantDTOList = merchantPage.getContent()
                .stream()
                .map(MerchantDTOAssembler::convertToDTO)
                .sorted(Comparator.comparing(MerchantDTO::getMerchantName, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return PageableDTOAssembler.convertToDTO(merchantDTOList, (int) merchantPage.getTotalElements(), merchantPage.getTotalPages());

    }

    public String login(MerchantDTO merchantDTO) {
        Optional<Merchant> merchant = merchantRepository
                .getByMerchantNameAndPassword(merchantDTO.getMerchantName(), merchantDTO.getPassword());
        if (merchant.isPresent() && merchant.get().isActive()) {
            return TokenUtil.sign(merchant.get().getId(), merchant.get().getMerchantName(), merchant.get().getType());
        } else {
            throw new MerchantException(MerchantException.MERCHANT_NO_EXIST);
        }
    }

    public MerchantDTO getMerchantById() {
        String merchantId = UserUtils.getUserId();
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantException(MerchantException.MERCHANT_NO_EXIST));

        return MerchantDTOAssembler.convertToDTO(merchant);
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
