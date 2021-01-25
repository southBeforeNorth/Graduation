package com.backend.feature.maintenance.user.service;

import com.backend.feature.maintenance.user.assembler.UserDTOAssembler;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import com.backend.feature.maintenance.user.exception.UserException;
import com.backend.feature.maintenance.user.repository.UserRepository;
import com.backend.util.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        User user = UserDTOAssembler.convertToEntity(userDTO);
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserException(UserException.USER_IS_EXIST);
        }
        return UserDTOAssembler.convertToDTO(userRepository.save(user));
    }

    public List<String> getUserNameList() {
        return userRepository.findAll().stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public String login(UserDTO userDTO) {
        Optional<User> user = userRepository.findByNameAndPassword(userDTO.getName(), userDTO.getPassword());
        if (!user.isPresent()) {
            throw new UserException(UserException.USER_NO_EXIST);
        } else {
            return TokenUtil.sign(user.get());
        }
    }
}
