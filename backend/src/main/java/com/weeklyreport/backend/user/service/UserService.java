package com.weeklyreport.backend.user.service;

import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.exceptions.ObjNotFoundException;
import com.weeklyreport.backend.user.dto.UserDTO;
import com.weeklyreport.backend.user.entity.AppUser;
import com.weeklyreport.backend.user.entity.Role;
import com.weeklyreport.backend.user.enums.RolesEnum;
import com.weeklyreport.backend.user.repo.RoleRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
    }

    public void promote(String id){
        try{
            AppUser user = userRepo.findById(id)
                    .orElseThrow(
                            () -> new ObjNotFoundException("User not found")
                    );

            Role managerRole =
                    roleRepo.findByName(RolesEnum.MANAGER)
                            .orElseThrow();

            user.setRole(managerRole);

            userRepo.save(user);
        } catch (ObjNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error promoting user");
        }
    }

    public void demote(String id){
        try{
            AppUser user = userRepo.findById(id)
                    .orElseThrow(
                            () -> new ObjNotFoundException("User not found")
                    );

            Role memberRole =
                    roleRepo.findByName(RolesEnum.MEMBER)
                            .orElseThrow();

            user.setRole(memberRole);

            userRepo.save(user);
        } catch (ObjNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error demoting user");
        }
    }

    public UserDTO getUserById(String id){
        try {
            AppUser user = userRepo.findById(id).orElseThrow(() -> new ObjNotFoundException("User not found"));
            return modelMapper.map(user, UserDTO.class);
        } catch (ObjNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error getting user : " + ex.getMessage());
        }
    }

    // Current user
    public AppUser getCurrentUser(){

        try {
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            return (AppUser) authentication.getPrincipal();
        } catch (Exception ex){
            throw new ServiceUnavailableException(ex.getMessage());
        }
    }

    public UserDTO getUser(){
        AppUser user = getCurrentUser();

        UserDTO dto = new UserDTO();
        dto.setId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }

    public List<UserDTO> getAllUsers() {
        try {
            List<AppUser> users = userRepo.findAll();

            if(users.isEmpty()){
                throw new ObjNotFoundException("Users list empty");
            }
            return modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
        } catch (ObjNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error getting user : " + ex.getMessage());
        }
    }
}
