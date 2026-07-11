package com.weeklyreport.backend.user.controller;

import com.weeklyreport.backend.response.CustomResponse;
import com.weeklyreport.backend.user.dto.RetrieveUserDTO;
import com.weeklyreport.backend.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public CustomResponse<RetrieveUserDTO> getCurrentUser(){
        RetrieveUserDTO user = userService.getOwner();
        return new CustomResponse<>(true, "User fetched success", user);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public CustomResponse<RetrieveUserDTO> getUserById(@PathVariable String id){
        RetrieveUserDTO user = userService.getUserById(id);
        return new CustomResponse<>(true, "User fetched success", user);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/all")
    public CustomResponse<List<RetrieveUserDTO>> getUserById(){
        List<RetrieveUserDTO> user = userService.getAllUsers();
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

    @GetMapping("/test")
    public Object test(Authentication authentication){

        return authentication.getAuthorities();
    }


}
