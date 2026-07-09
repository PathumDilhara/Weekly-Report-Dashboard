package com.weeklyreport.backend.project.controller;

import com.weeklyreport.backend.project.dto.CreateProjectDTO;
import com.weeklyreport.backend.project.dto.ProjectResponseDTO;
import com.weeklyreport.backend.project.service.ProjectService;
import com.weeklyreport.backend.response.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // MEMBER

    @GetMapping
    public CustomResponse<List<CreateProjectDTO>> getAll(){
        return new CustomResponse<>(true, "All", projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public CustomResponse<CreateProjectDTO> getProjectById(@PathVariable Long id){
        return new CustomResponse<>(true, "Project fetched id :" + id, projectService.getProjectById(id));
    }

    // Manager

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public CustomResponse<ProjectResponseDTO> createProject(@RequestBody CreateProjectDTO dto){
        ProjectResponseDTO res = projectService.createProject(dto);
        return new CustomResponse<>(true, "project created", res);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public CustomResponse<ProjectResponseDTO> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody CreateProjectDTO dto
    ){
        ProjectResponseDTO res = projectService.updateProject(id, dto);
        return new CustomResponse<>(true, "project created", res);
    }

}
