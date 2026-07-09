package com.weeklyreport.backend.user.controller;

import com.weeklyreport.backend.response.CustomResponse;
import com.weeklyreport.backend.user.dto.UserDTO;
import com.weeklyreport.backend.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{id}")
    public CustomResponse<UserDTO> getUserById(@PathVariable String id){
        UserDTO user = userService.getUserById(id);
        return new CustomResponse<>(true, "User fetched success", user);
    }

    @PutMapping("/{id}/promote")
    @PreAuthorize("hasRole('MANAGER')")
    public CustomResponse<?> promoteUser(
            @PathVariable String id
    ){

        userService.promote(id);

        return new CustomResponse<> (true, "User promoted to manager", null);
    }

    @PutMapping("/{id}/demote")
    @PreAuthorize("hasRole('MANAGER')")
    public CustomResponse<?> demoteUser(
            @PathVariable String id
    ){

        userService.demote(id);

        return new CustomResponse<> (true, "User demoted to member", null);
    }

}
