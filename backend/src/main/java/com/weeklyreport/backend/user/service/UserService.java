package com.weeklyreport.backend.user.service;

import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.exceptions.UserNotFoundException;
import com.weeklyreport.backend.user.dto.UserDTO;
import com.weeklyreport.backend.user.entity.AppUser;
import com.weeklyreport.backend.user.entity.Role;
import com.weeklyreport.backend.user.enums.RolesEnum;
import com.weeklyreport.backend.user.repo.RoleRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
                            () -> new UserNotFoundException("User not found")
                    );

            Role managerRole =
                    roleRepo.findByName(RolesEnum.MANAGER)
                            .orElseThrow();

            user.setRole(managerRole);

            userRepo.save(user);
        } catch (UserNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error promoting user");
        }
    }

    public void demote(String id){
        try{
            AppUser user = userRepo.findById(id)
                    .orElseThrow(
                            () -> new UserNotFoundException("User not found")
                    );

            Role memberRole =
                    roleRepo.findByName(RolesEnum.MEMBER)
                            .orElseThrow();

            user.setRole(memberRole);

            userRepo.save(user);
        } catch (UserNotFoundException ex){
            throw ex;
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error demoting user");
        }
    }

    public UserDTO getUserById(String id){
        try {
            AppUser user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            return modelMapper.map(user, UserDTO.class);
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error getting user");
        }
    }

    // Current user
    public AppUser getCurrentUser(){

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (AppUser) authentication.getPrincipal();

    }

}
