package com.weeklyreport.backend.user.init;

import com.weeklyreport.backend.user.entity.Role;
import com.weeklyreport.backend.user.enums.RolesEnum;
import com.weeklyreport.backend.user.repo.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RoleInitializer.class);

    private final RoleRepo roleRepo;

    public RoleInitializer(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        for(RolesEnum rolesEnum : RolesEnum.values()){
            String roleName = rolesEnum.name();
            if(!roleRepo.existsByName(roleName)){
                Role role = new Role();
                role.setName(roleName);
                roleRepo.save(role);
                log.info("### Role Initializer inserted roel : {}", roleName);
            }
        }
    }
}
