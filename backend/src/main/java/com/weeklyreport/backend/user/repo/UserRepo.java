package com.weeklyreport.backend.user.repo;

import com.weeklyreport.backend.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, String> {

//    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
