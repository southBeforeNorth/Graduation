package com.backend.feature.maintenance.user.controller;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.comment.dto.PageableDTO;
import com.backend.comment.util.PageableUtils;
import com.backend.feature.maintenance.user.dto.UserDTO;
import com.backend.feature.maintenance.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public CommonDTO<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return CommonDTOAssembler.convertTODTO(userService.create(userDTO));
    }

    @PostMapping("/login")
    public CommonDTO<String> login(@RequestBody UserDTO userDTO) {
        return CommonDTOAssembler.convertTODTO(userService.login(userDTO));
    }

    @GetMapping("/nameList")
    public CommonDTO<List<String>> getUserNameList() {
        return CommonDTOAssembler.convertTODTO(userService.getUserNameList());
    }

    @PutMapping("/uploadHeader/{id}")
    public CommonDTO<UserDTO> uploadHeader(@PathVariable String id) {
        return CommonDTOAssembler.convertTODTO(userService.uploadUserHeaderById(id));
    }

    @PutMapping("/updateByOwner")
    public CommonDTO<UserDTO> updateUserByOwner(@RequestBody UserDTO userDTO) {
        return CommonDTOAssembler.convertTODTO(userService.updateUserByOwner(userDTO));
    }

    @GetMapping("/changePassword")
    public CommonDTO<UserDTO> changeUserPasswordByOwner(@RequestParam String newPassword,
                                                        @RequestParam String oldPassword) {
        return CommonDTOAssembler.convertTODTO(userService.changeUserPassword(oldPassword, newPassword));
    }

    @DeleteMapping("/delete/{id}")
    public CommonDTO<UserDTO> delete(@PathVariable String id) {
        return CommonDTOAssembler.convertTODTO(userService.delete(id));
    }

    @PutMapping("/updateByManager/{id}")
    public CommonDTO<UserDTO> updateUserByManager(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return CommonDTOAssembler.convertTODTO(userService.updateUserByManager(id, userDTO));
    }

    @GetMapping("/page")
    public CommonDTO<PageableDTO<UserDTO>> getUserListByPage(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam int start,
            @RequestParam(required = false) String userName
    ) {
        PageRequest pageRequest = PageableUtils.getPageable(page, pageSize, start);
        return userService.getUserListByPage(pageRequest, userName);

    }

    @GetMapping("/userId")
    public CommonDTO<UserDTO> getUserById() {
        return CommonDTOAssembler.convertTODTO(userService.getUserById());
    }

    @GetMapping("/StringId/{id}")
    public CommonDTO<UserDTO> getUserByStringId(@PathVariable String id) {
        return CommonDTOAssembler.convertTODTO(userService.getUserByStringId(id));
    }
}
