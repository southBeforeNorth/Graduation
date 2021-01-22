package com.backend.feature.maintenance.user.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.feature.maintenance.user.assembler.UserDTOAssembler;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.entity.User;
import com.backend.feature.maintenance.user.exception.UserException;
import com.backend.feature.maintenance.user.service.UserService;
import com.backend.util.token.TokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserDTO getUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("delue");
        userDTO.setSex("男");
        throw new UserException(UserException.USER_NO_EXIST);
    }

    @PostMapping
    public CommonDTO<UserDTO> createUser(@RequestBody UserDTO userDTO) {
       return CommonDTOAssembler.convertTODTO(userService.create(userDTO));
    }

    @PostMapping("/login")
    public CommonDTO<String> login(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        return CommonDTOAssembler.convertTODTO(TokenUtil.sign(user));
    }

    @GetMapping("/nameList")
    public CommonDTO<List<String>> getUserNameList() {
        return CommonDTOAssembler.convertTODTO(userService.getUserNameList());
    }
}
