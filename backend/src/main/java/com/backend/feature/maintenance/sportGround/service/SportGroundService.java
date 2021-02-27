package com.backend.feature.maintenance.sportGround.service;

import com.backend.comment.assembler.PageableDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.UserUtils;
import com.backend.feature.maintenance.merchant.assembler.MerchantDTOAssembler;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.merchant.repository.MerchantRepository;
import com.backend.feature.maintenance.sportGround.assembler.SportGroundDTOAssembler;
import com.backend.feature.maintenance.sportGround.dto.SportGroundDTO;
import com.backend.feature.maintenance.sportGround.entity.SportGround;
import com.backend.feature.maintenance.sportGround.exception.SportGroundException;
import com.backend.feature.maintenance.sportGround.repository.PictureRepository;
import com.backend.feature.maintenance.sportGround.repository.SportGroundRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SportGroundService {

    @Autowired
    private SportGroundRepository sportGroundRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private PictureRepository pictureRepository;

    public SportGroundDTO create(SportGroundDTO sportGroundDTO) {
        SportGround sportGround = SportGroundDTOAssembler.convertToEntity(sportGroundDTO);
        String id = UserUtils.getUserId();
        Optional<Merchant> merchant = merchantRepository.findById(id);
        merchant.ifPresent(sportGround::setMerchant);
        return SportGroundDTOAssembler.convertToDTO(sportGroundRepository.save(sportGround));
    }

    public MerchantDTO getMerchantById(String id) {
        SportGround sportGround = sportGroundRepository.findById(id)
                .orElseThrow(() -> new SportGroundException(SportGroundException.SPORT_GROUND_NO_EXIST));

        return MerchantDTOAssembler.convertToDTO(sportGround.getMerchant());
    }

    public SportGroundDTO delete(String id) {
        SportGround sportGround = sportGroundRepository.findById(id)
                .orElseThrow(() -> new SportGroundException(SportGroundException.SPORT_GROUND_NO_EXIST));
        sportGroundRepository.deleteById(id);
        return SportGroundDTOAssembler.convertToDTO(sportGround);
    }

    public SportGroundDTO update(String id, SportGroundDTO sportGroundDTO) {
        SportGround sportGround = sportGroundRepository.findById(id)
                .orElseThrow(() -> new SportGroundException(SportGroundException.SPORT_GROUND_NO_EXIST));
        pictureRepository.deleteAll(sportGround.getPictures());
        SportGround saveSportGround = SportGroundDTOAssembler.convertToEntity(sportGroundDTO);
        saveSportGround.setId(id);
        saveSportGround.setMerchant(sportGround.getMerchant());
        SportGround updateSportGround = sportGroundRepository.saveAndFlush(saveSportGround);
        return SportGroundDTOAssembler.convertToDTO(updateSportGround);
    }

    public SportGroundDTO getById(String id) {
        SportGround sportGround = sportGroundRepository.findById(id)
                .orElseThrow(() -> new SportGroundException(SportGroundException.SPORT_GROUND_NO_EXIST));
        return SportGroundDTOAssembler.convertToDTO(sportGround);
    }

    public CommonDTO<PageableDTO<SportGroundDTO>> getSportGroundListById(
            PageRequest pageRequest,
            String name,
            String city) {
        String id = UserUtils.getUserId();
        if (StringUtils.equals(UserUtils.getUserType(), "manage")) {
            id = null;
        }
        Specification<SportGround> specification = getSportGroundSpecification(name, city, id);
        return this.getSportGroundPage(specification, pageRequest);
    }

    public CommonDTO<PageableDTO<SportGroundDTO>> getSportGroundList(
            PageRequest pageRequest,
            String name,
            String city) {
        Specification<SportGround> specification = getSportGroundSpecification(name, city, null);
        return this.getSportGroundPage(specification, pageRequest);
    }

    private CommonDTO<PageableDTO<SportGroundDTO>> getSportGroundPage(
            Specification<SportGround> specification,
            PageRequest pageRequest) {
        Page<SportGround> sportGroundPage = sportGroundRepository.findAll(specification, pageRequest);
        List<SportGroundDTO> merchantDTOList = sportGroundPage.getContent()
                .stream()
                .map(SportGroundDTOAssembler::convertToDTO)
                .sorted(Comparator.comparing(SportGroundDTO::getName, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        return PageableDTOAssembler.convertToDTO(merchantDTOList, (int) sportGroundPage.getTotalElements(), sportGroundPage.getTotalPages());
    }

    private Specification<SportGround> getSportGroundSpecification(String name, String city, String id) {
        return (root, query, cb) -> {
            List<Predicate> predicateOr = new ArrayList<>();
            List<Predicate> predicateList = new ArrayList<>();
            Predicate conditionPre;

            if (StringUtils.isNotEmpty(name)) {
                predicateOr.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.isNotEmpty(city)) {
                predicateOr.add(cb.like(root.get("city"), "%" + city + "%"));
            }

            Predicate[] or = new Predicate[predicateOr.size()];

            if (Objects.nonNull(id)) {
                Join<SportGround, Merchant> merchantJoin
                        = root.join(root.getModel().getSingularAttribute("merchant", Merchant.class), JoinType.LEFT);
                predicateList.add(cb.equal(merchantJoin.get("id"), id));

                Predicate[] and = new Predicate[predicateList.size()];

                conditionPre = query.where(
                        cb.and(predicateOr.toArray(or)),
                        cb.and(predicateList.toArray(and)))
                        .getRestriction();

            } else if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(city)) {
                conditionPre = query.where(
                        cb.or(predicateOr.toArray(or)))
                        .getRestriction();
            } else {
                conditionPre = query.where(
                        cb.and(predicateOr.toArray(or)))
                        .getRestriction();
            }
            return conditionPre;
        };
    }
}
