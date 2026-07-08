package com.weeklyreport.backend.user.controller;

import com.weeklyreport.backend.response.CustomResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {

    @GetMapping
    public CustomResponse<String> getUserById(){
        return new CustomResponse<>(true, "User fetched success", "User");
    }
}
