package com.weeklyreport.backend.project.repo;

import com.weeklyreport.backend.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByActiveTrue();
}
