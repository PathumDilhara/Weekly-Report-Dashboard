package com.weeklyreport.backend.user.controller;

import com.weeklyreport.backend.response.CustomResponse;
import com.weeklyreport.backend.user.dto.AuthResponseDTO;
import com.weeklyreport.backend.user.dto.LoginRequestDTO;
import com.weeklyreport.backend.user.dto.RegisterRequestDTO;
import com.weeklyreport.backend.user.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public CustomResponse<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto){
        AuthResponseDTO result = authService.register(dto);
        return new CustomResponse<>(true, "User registered successfully", result);
    }

    @PostMapping("/login")
    public CustomResponse<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto){
        AuthResponseDTO res = authService.login(dto);
        return new CustomResponse<>(true, "User login success", res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        authService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/test")
    public Object test(Authentication authentication){

        return authentication.getAuthorities();
    }

}
