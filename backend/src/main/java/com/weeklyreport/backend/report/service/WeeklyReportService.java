package com.weeklyreport.backend.report.service;

import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.project.entity.Project;
import com.weeklyreport.backend.project.repo.ProjectRepo;
import com.weeklyreport.backend.report.dto.CreateReportRequestDTO;
import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import com.weeklyreport.backend.report.dto.UpdateReportRequestDTO;
import com.weeklyreport.backend.report.entity.WeeklyReport;
import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import com.weeklyreport.backend.report.repo.WeeklyReportRepo;
import com.weeklyreport.backend.user.entity.AppUser;
import com.weeklyreport.backend.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WeeklyReportService {

    private final WeeklyReportRepo reportRepo;
    private final UserService userService;
    private final ProjectRepo projectRepo;

    public WeeklyReportService(WeeklyReportRepo reportRepo, UserService userService, ProjectRepo projectRepo) {
        this.reportRepo = reportRepo;
        this.userService = userService;
        this.projectRepo = projectRepo;
    }

    public ReportResponseDTO createReport(CreateReportRequestDTO dto) {
       try{
           AppUser user = userService.getCurrentUser();

           Project project = projectRepo.findById(dto.getProjectId())
                   .orElseThrow(() -> new RuntimeException("Project not found"));

           WeeklyReport report = new WeeklyReport();

           report.setAppUser(user);
           report.setProject(project);
           report.setWeekStart(dto.getWeekStart());
           report.setWeekEnd(dto.getWeekEnd());
           report.setTasksCompleted(dto.getTasksCompleted());
           report.setTasksPlanned(dto.getTasksPlanned());
           report.setBlockers(dto.getBlockers());
           report.setHoursWorked(dto.getHoursWorked());
           report.setStatus(ReportStatusEnum.DRAFT);

           WeeklyReport saved = reportRepo.save(report);

           return convertToDTO(saved);

       } catch (Exception ex){
           throw new ServiceUnavailableException("Error Creating Report : "+ex.getMessage());
       }
    }

    public ReportResponseDTO updateReport(Long reportId, UpdateReportRequestDTO dto) {
        WeeklyReport report = reportRepo.findById(reportId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found")
                );

        if(report.getStatus() == ReportStatusEnum.DRAFT){
            throw new RuntimeException(
                    "Submitted report cannot be updated"
            );
        }

        report.setTasksCompleted(dto.getTasksCompleted());
        report.setTasksPlanned(dto.getTasksPlanned());
        report.setBlockers(dto.getBlockers());
        report.setHoursWorked(dto.getHoursWorked());

        WeeklyReport updated = reportRepo.save(report);

        return convertToDTO(updated);
    }

    public void deleteReport(Long reportId) {
        try{
            WeeklyReport report = reportRepo.findById(reportId)
                    .orElseThrow(() ->
                            new RuntimeException("Report not found")
                    );


            if(report.getStatus() == ReportStatusEnum.SUBMITTED){
                throw new RuntimeException(
                        "Submitted report cannot be deleted"
                );
            }

            reportRepo.delete(report);
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error updating report");
        }
    }

    public ReportResponseDTO submitReport(Long reportId) {
        try{
            WeeklyReport report = reportRepo.findById(reportId)
                    .orElseThrow(() ->
                            new RuntimeException("Report not found")
                    );


            report.setStatus(ReportStatusEnum.SUBMITTED);


            WeeklyReport saved = reportRepo.save(report);

            return convertToDTO(saved);
        } catch (Exception ex){
            throw new ServiceUnavailableException("Error submitting report");
        }
    }

    public ReportResponseDTO getReportById(Long reportId) {
        WeeklyReport report = reportRepo.findById(reportId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found")
                );

        return convertToDTO(report);
    }

    public List<ReportResponseDTO> getMyReports() {
        AppUser user = userService.getCurrentUser();

        List<WeeklyReport> reports = reportRepo.findByAppUserUserId(user.getUserId());
        List<ReportResponseDTO> responseList = new ArrayList<>();

        for (WeeklyReport report : reports) {
            ReportResponseDTO dto = convertToDTO(report);
            responseList.add(dto);
        }

        return responseList;
    }

    private ReportResponseDTO convertToDTO(WeeklyReport report) {
       ReportResponseDTO dto = new ReportResponseDTO();

       dto.setId(report.getId());
       dto.setProjectId(report.getProject().getId());
       dto.setProjectName(report.getProject().getName());
       dto.setUserId(report.getAppUser().getUserId());
       dto.setUserName(report.getAppUser().getFirstName() + " " + report.getAppUser().getLastName());
       dto.setWeekStart(report.getWeekStart());
       dto.setWeekEnd(report.getWeekEnd());
       dto.setTasksCompleted(report.getTasksCompleted());
       dto.setTasksPlanned(report.getTasksPlanned());
       dto.setBlockers(report.getBlockers());
       dto.setHoursWorked(report.getHoursWorked());
       dto.setStatus(report.getStatus());
       dto.setSubmittedAt(report.getSubmittedAt());
       dto.setCreatedAt(report.getCreatedAt());
       dto.setUpdatedAt(report.getUpdatedAt());

       return dto;
    }
}
