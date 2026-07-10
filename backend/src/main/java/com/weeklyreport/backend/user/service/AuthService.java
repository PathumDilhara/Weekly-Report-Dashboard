package com.weeklyreport.backend.user.service;

import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.exceptions.UserAlreadyExistsException;
import com.weeklyreport.backend.user.dto.AuthResponseDTO;
import com.weeklyreport.backend.user.dto.LoginRequestDTO;
import com.weeklyreport.backend.user.dto.RegisterRequestDTO;
import com.weeklyreport.backend.user.entity.AppUser;
import com.weeklyreport.backend.user.entity.Role;
import com.weeklyreport.backend.user.enums.RolesEnum;
import com.weeklyreport.backend.user.repo.RoleRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo, RoleRepo roleRepo, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // User registration
    public AuthResponseDTO register(RegisterRequestDTO dto){

        try {
            if (userRepo.existsByEmail(dto.getEmail())) {
                throw new UserAlreadyExistsException("User already exists : " + dto.getEmail());
            }


            // adding role member as default
            Role defultRole = roleRepo.findByName(RolesEnum.MEMBER).orElseThrow(
                    () -> new RuntimeException("Role not found"));

            AppUser user = new AppUser();
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPassword(
                    passwordEncoder.encode(
                            dto.getPassword()
                    )
            );
            user.setRole(defultRole);

            AppUser savedUser = userRepo.save(user);

            String token =
                    jwtService.generateToken(savedUser);

            return new AuthResponseDTO(token);

        } catch (UserAlreadyExistsException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Something went wrong");
        }
    }

    public AuthResponseDTO login(LoginRequestDTO dto){
        try {

            System.out.println("EMAIL = " + dto.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );

            var user = (UserDetails) authentication.getPrincipal();
            System.out.println("USER FOUND = " + user.getUsername());


            String token = jwtService.generateToken(Objects.requireNonNull(user));

            return new AuthResponseDTO(token);
        } catch (Exception ex){
            throw new RuntimeException("User login error" + ex.getMessage());
        }
    }

    // delete user
    public void delete(String id){
        try{
            userRepo.deleteById(id);
        } catch (Exception ex){
            throw new ServiceUnavailableException("Server error : " +ex.getMessage());
        }
    }
}
