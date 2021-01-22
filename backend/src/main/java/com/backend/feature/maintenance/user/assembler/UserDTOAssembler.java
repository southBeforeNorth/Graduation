package com.backend.feature.maintenance.user.assembler;

import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import org.springframework.beans.BeanUtils;

public class UserDTOAssembler {
    private UserDTOAssembler(){

    }

    public static User convertToEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
