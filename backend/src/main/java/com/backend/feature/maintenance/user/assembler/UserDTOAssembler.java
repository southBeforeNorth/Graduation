package com.backend.feature.maintenance.user.assembler;

import com.backend.feature.maintenance.picture.assembler.PictureDTOAssembler;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class UserDTOAssembler {
    private UserDTOAssembler(){

    }

    public static User convertToEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        if(Objects.nonNull(userDTO.getHeader())){
            user.setHeader(PictureDTOAssembler.convertToEntity(userDTO.getHeader()));
        }
        return user;
    }

    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        if(Objects.nonNull(user.getHeader())){
            userDTO.setHeader(PictureDTOAssembler.convertToDTO(user.getHeader()));
        }
        return userDTO;
    }
}
