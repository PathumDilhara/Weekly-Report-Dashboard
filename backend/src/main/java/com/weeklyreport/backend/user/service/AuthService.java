package com.weeklyreport.backend.user.service;

import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.exceptions.UserAlreadyExistsException;
import com.weeklyreport.backend.user.dto.AuthResponseDTO;
import com.weeklyreport.backend.user.dto.RegisterRequestDTO;
import com.weeklyreport.backend.user.entity.AppUser;
import com.weeklyreport.backend.user.entity.Role;
import com.weeklyreport.backend.user.enums.RolesEnum;
import com.weeklyreport.backend.user.repo.RoleRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;

    public AuthService(UserRepo userRepo, RoleRepo roleRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
    }

    // User registration
    public AuthResponseDTO register(RegisterRequestDTO dto){

        try {
            if (userRepo.existsByEmail(dto.getEmail())) {
                throw new UserAlreadyExistsException("User already exists : " + dto.getEmail());
            }

            AppUser user = modelMapper.map(dto, AppUser.class);

            // adding role member as default
            Role defultRole = roleRepo.findByName(RolesEnum.member.name()).orElseThrow(
                    () -> new RuntimeException("Role not found"));

            user.getRoles().add(defultRole);

            AppUser savedUser = userRepo.save(user);
            return modelMapper.map(savedUser, AuthResponseDTO.class);
        } catch (UserAlreadyExistsException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Something went wrong");
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
