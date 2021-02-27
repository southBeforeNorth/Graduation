package com.backend.feature.maintenance.user.service;

import com.backend.feature.maintenance.user.assembler.UserDTOAssembler;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import com.backend.feature.maintenance.user.exception.UserException;
import com.backend.feature.maintenance.user.repository.UserRepository;
import com.backend.util.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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
        Optional<User> user = userRepository
                .findByNameAndPasswordAndType(userDTO.getName(), userDTO.getPassword(), userDTO.getType());
        if (!user.isPresent()) {
            throw new UserException(UserException.USER_NO_EXIST);
        } else {
            return TokenUtil.sign(user.get().getId(), user.get().getName(), user.get().getType());
        }
    }

    public UserDTO getUserById(){
        String userId = this.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new UserException(UserException.USER_NO_EXIST);
        } else {
            return UserDTOAssembler.convertToDTO(user.get());
        }
    }

    private String getUserId(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return request.getAttribute("id").toString();
    }
}
