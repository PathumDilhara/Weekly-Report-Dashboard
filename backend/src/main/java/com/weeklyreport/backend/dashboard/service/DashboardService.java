package com.weeklyreport.backend.dashboard.service;

import com.weeklyreport.backend.dashboard.dto.DashboardSummaryDTO;
import com.weeklyreport.backend.dashboard.dto.ReportStatusDTO;
import com.weeklyreport.backend.dashboard.dto.WorkloadDTO;
import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.project.repo.ProjectRepo;
import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import com.weeklyreport.backend.report.entity.WeeklyReport;
import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import com.weeklyreport.backend.report.repo.WeeklyReportRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DashboardService {

    private final WeeklyReportRepo reportRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    public DashboardService(WeeklyReportRepo reportRepo, UserRepo userRepo, ProjectRepo projectRepo) {
        this.reportRepo = reportRepo;
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    public DashboardSummaryDTO getSummary() {
        try{
            long totalUsers = userRepo.count();

            long totalProjects = projectRepo.count();

            long totalReports = reportRepo.count();

            long submittedReports =
                    reportRepo.countByStatus(ReportStatusEnum.SUBMITTED);

            long draftReports =
                    reportRepo.countByStatus(ReportStatusEnum.DRAFT);


            return new DashboardSummaryDTO(
                    totalUsers,
                    totalProjects,
                    totalReports,
                    submittedReports,
                    draftReports
            );
        } catch (Exception ex){
            throw new ServiceUnavailableException(ex.getMessage());
        }
    }

    public List<ReportResponseDTO> getAllReports() {
        return reportRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ReportStatusDTO> getSubmissionStatus() {
       try {
           return reportRepo.findAll()
                   .stream()
                   .map(report -> new ReportStatusDTO(
                           report.getAppUser().getFirstName(),
                           report.getProject().getName(),
                           report.getWeekStart(),
                           report.getWeekEnd(),
                           report.getStatus()
                   ))
                   .toList();
       } catch (Exception ex){
           throw new ServiceUnavailableException(ex.getMessage());
       }
    }

    public List<WorkloadDTO> getWorkload() {
       try {
           return reportRepo.findAll()
                   .stream()
                   .collect(Collectors.groupingBy(
                           report -> report.getAppUser().getUserId(),
                           Collectors.summingInt(
                                   WeeklyReport::getHoursWorked
                           )
                   ))
                   .entrySet()
                   .stream()
                   .map(entry ->
                           new WorkloadDTO(
                                   entry.getKey(),
                                   entry.getValue()
                           )
                   )
                   .toList();
       } catch (Exception ex){
           throw new ServiceUnavailableException(ex.getMessage());
       }
    }

    private ReportResponseDTO convertToDTO(WeeklyReport report){
        ReportResponseDTO dto = new ReportResponseDTO();

        dto.setId(report.getId());

        dto.setWeekStart(report.getWeekStart());

        dto.setWeekEnd(report.getWeekEnd());

        dto.setTasksCompleted(report.getTasksCompleted());

        dto.setTasksPlanned(report.getTasksPlanned());

        dto.setBlockers(report.getBlockers());

        dto.setHoursWorked(report.getHoursWorked());

        dto.setStatus(report.getStatus());

        // User details
        if (report.getAppUser() != null) {
            dto.setUserId(report.getAppUser().getUserId());
            dto.setUserName(
                    report.getAppUser().getFirstName()
                            + " "
                            + report.getAppUser().getLastName()
            );
        }


        // Project details
        if (report.getProject() != null) {
            dto.setProjectId(report.getProject().getId());
            dto.setProjectName(report.getProject().getName());
        }


        dto.setSubmittedAt(report.getSubmittedAt());


        return dto;
    }
}