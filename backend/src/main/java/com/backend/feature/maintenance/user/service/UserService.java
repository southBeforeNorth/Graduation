package com.backend.feature.maintenance.user.service;

import com.backend.comment.assembler.PageableDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.UserUtils;
import com.backend.feature.maintenance.merchant.assembler.MerchantDTOAssembler;
import com.backend.feature.maintenance.merchant.dto.MerchantDTO;
import com.backend.feature.maintenance.merchant.entity.Merchant;
import com.backend.feature.maintenance.merchant.exception.MerchantException;
import com.backend.feature.maintenance.picture.entity.Picture;
import com.backend.feature.maintenance.picture.exception.PictureException;
import com.backend.feature.maintenance.picture.repository.PictureRepository;
import com.backend.feature.maintenance.user.assembler.UserDTOAssembler;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import com.backend.feature.maintenance.user.exception.UserException;
import com.backend.feature.maintenance.user.repository.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    public UserDTO create(UserDTO userDTO) {
        User user = UserDTOAssembler.convertToEntity(userDTO);
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserException(UserException.USER_IS_EXIST);
        }
        return UserDTOAssembler.convertToDTO(userRepository.save(user));
    }

    public UserDTO delete(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserException.USER_NO_EXIST));
        userRepository.deleteById(id);
        return UserDTOAssembler.convertToDTO(user);
    }

    public UserDTO updateUserByManager(String id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserException.USER_NO_EXIST));
        Optional<User> optionalUser =
                userRepository.findByName(userDTO.getName());
        if (optionalUser.isPresent() && !StringUtils.equals(user.getId(), optionalUser.get().getId())) {
            throw new UserException(UserException.USER_IS_EXIST);
        }
        user.setName(userDTO.getName());
        user.setSex(userDTO.getSex());
        user.setPhone(userDTO.getPhone());
        user.setBirthDay(userDTO.getBirthDay());
        user.setPassword(userDTO.getPassword());
        return UserDTOAssembler.convertToDTO(userRepository.saveAndFlush(user));
    }

    public UserDTO uploadUserHeaderById(String id) {
        Picture header = pictureRepository.findById(id)
                .orElseThrow(() -> new PictureException(PictureException.PICTURE_NOT_EXIST));
        User user = userRepository.findById(UserUtils.getUserId())
                .orElseThrow(() -> new UserException(UserException.USER_NO_EXIST));
        if (Objects.nonNull(user.getHeader())) {
            pictureRepository.deleteById(user.getHeader().getId());
        }
        user.setHeader(header);
        return UserDTOAssembler.convertToDTO(userRepository.saveAndFlush(user));
    }

    public List<String> getUserNameList() {
        return userRepository.findAll().stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public String login(UserDTO userDTO) {
        Optional<User> user = userRepository
                .findByNameAndPasswordAndType(userDTO.getName(), userDTO.getPassword(), userDTO.getType());
        if (!user.isPresent()) {
            throw new UserException(UserException.USER_NO_EXIST);
        } else {
            return TokenUtil.sign(user.get().getId(), user.get().getName(), user.get().getType());
        }
    }

    public UserDTO changeUserPassword(String oldPassword, String newPassword) {

        String userId = UserUtils.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserException.USER_NO_EXIST));
        if (StringUtils.equals(user.getPassword(), oldPassword)) {
            user.setPassword(newPassword);
            return UserDTOAssembler.convertToDTO(userRepository.saveAndFlush(user));
        }
        return new UserDTO();
    }

    public UserDTO updateUserByOwner(UserDTO userDTO) {
        User user = userRepository.findById(UserUtils.getUserId())
                .orElseThrow(() -> new UserException(UserException.USER_NO_EXIST));
        user.setName(userDTO.getName());
        user.setBirthDay(userDTO.getBirthDay());
        user.setPhone(userDTO.getPhone());
        user.setSex(userDTO.getSex());
        return UserDTOAssembler.convertToDTO(userRepository.saveAndFlush(user));
    }

    public UserDTO getUserById() {
        String userId = UserUtils.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserException(UserException.USER_NO_EXIST);
        } else {
            return UserDTOAssembler.convertToDTO(user.get());
        }
    }


    public CommonDTO<PageableDTO<UserDTO>> getUserListByPage(
            PageRequest pageRequest,
            String userName) {
        Specification<User> specification = getUserSpecification(userName);
        Page<User> userPage = userRepository.findAll(specification, pageRequest);
        List<UserDTO> userDTOList = userPage.getContent()
                .stream()
                .map(UserDTOAssembler::convertToDTO)
                .sorted(Comparator.comparing(UserDTO::getName, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return PageableDTOAssembler.convertToDTO(userDTOList, (int) userPage.getTotalElements(), userPage.getTotalPages());

    }

    private Specification<User> getUserSpecification(String userName) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (StringUtils.isNotEmpty(userName)) {
                predicateList.add(cb.like(root.get("name"), "%" + userName + "%"));
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            query.where(cb.and(predicateList.toArray(predicates)));
            return query.getRestriction();
        };
    }
}
