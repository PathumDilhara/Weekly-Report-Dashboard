package com.weeklyreport.backend.project.entity;

import com.weeklyreport.backend.response.CustomResponse;
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

    @GetMapping("/{id}")
    public CustomResponse<ProjectDTO> getProjectById(@PathVariable Long id){
        return new CustomResponse<>(true, "Project fetched id :" + id, projectService.getProjectById(id));
    }

    @PostMapping
    public CustomResponse<ProjectDTO> createProject(@RequestBody ProjectDTO dto){
        projectService.createProject(dto);
        return new CustomResponse<>(true, "project created", null);
    }

    @GetMapping
    public CustomResponse<List<ProjectDTO>> getAll(){
        return new CustomResponse<>(true, "All", projectService.getAllProjects());
    }
}
