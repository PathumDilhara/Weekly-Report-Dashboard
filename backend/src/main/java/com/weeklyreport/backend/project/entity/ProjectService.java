package com.weeklyreport.backend.project.entity;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepo projectRepo;
    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepo projectRepo, ModelMapper modelMapper) {
        this.projectRepo = projectRepo;
        this.modelMapper = modelMapper;
    }

    public void createProject(ProjectDTO dto){
        Project project = new Project();
        project.setName(dto.getName());

        projectRepo.save(project);
    }

    public ProjectDTO getProjectById(Long id){
        return modelMapper.map(projectRepo.findById(id), ProjectDTO.class);
    }

    public List<ProjectDTO> getAllProjects(){
        try {
            return modelMapper.map(projectRepo.findAll(), new TypeToken<List<ProjectDTO>>() {
            }.getType());
        }catch (Exception ex){
            throw new RuntimeException("Error : ", ex);
        }
    }
}
