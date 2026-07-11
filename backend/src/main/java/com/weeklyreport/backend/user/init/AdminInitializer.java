package com.weeklyreport.backend.user.init;

import com.weeklyreport.backend.user.entity.AppUser;
import com.weeklyreport.backend.user.entity.Role;
import com.weeklyreport.backend.user.enums.RolesEnum;
import com.weeklyreport.backend.user.enums.TeamEnum;
import com.weeklyreport.backend.user.repo.RoleRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(
            UserRepo userRepo,
            RoleRepo roleRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        String adminEmail = "admin@weeklyreport.com";

        if(!userRepo.existsByEmail(adminEmail)) {

            Role superAdminRole = roleRepo.findByName(RolesEnum.MANAGER)
                    .orElseThrow(() ->
                            new RuntimeException("SUPER_ADMIN role not found")
                    );

            AppUser admin = new AppUser();

            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setEmail(adminEmail);
            // default password
            admin.setPassword(
                    passwordEncoder.encode("Admin@123")
            );
            admin.setRole(superAdminRole);
            admin.setTeam(TeamEnum.BACKEND);

            userRepo.save(admin);

            log.info("### Super Admin initialized : {}", adminEmail);

        } else {
            log.info("### Super Admin already exists");
        }
    }
}
