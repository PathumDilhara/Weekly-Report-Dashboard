package com.weeklyreport.backend.project.service;

import com.weeklyreport.backend.exceptions.ObjNotFoundException;
import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.project.dto.CreateProjectDTO;
import com.weeklyreport.backend.project.dto.ProjectResponseDTO;
import com.weeklyreport.backend.project.entity.Project;
import com.weeklyreport.backend.project.repo.ProjectRepo;
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

    public ProjectResponseDTO createProject(CreateProjectDTO dto){
        try {
            Project project = new Project();
            project.setName(dto.getName());

            Project savedProject = projectRepo.save(project);

            return modelMapper.map(savedProject, ProjectResponseDTO.class);
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error creating project");
        }
    }

    public CreateProjectDTO getProjectById(Long id){
        return modelMapper.map(projectRepo.findById(id), CreateProjectDTO.class);
    }

    public List<CreateProjectDTO> getAllProjects(){
        try {
            return modelMapper.map(projectRepo.findAll(), new TypeToken<List<CreateProjectDTO>>() {
            }.getType());
        }catch (Exception ex){
            throw new RuntimeException("Error : ", ex);
        }
    }

    public ProjectResponseDTO updateProject(Long id, CreateProjectDTO dto) {
        try {
            Project project = projectRepo.findById(id).orElseThrow(
                    ()-> new ObjNotFoundException("Project not found : "+id));

            project.setName(dto.getName());
            project.setDescription(dto.getDescription());
            project.setStartDate(dto.getStartDate());
            project.setEndDate(dto.getEndDate());
            project.setActive(dto.isActive());

            Project updated = projectRepo.save(project);

            return modelMapper.map(updated, ProjectResponseDTO.class);
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error updating project : "+ ex.getMessage());
        }
    }
}
