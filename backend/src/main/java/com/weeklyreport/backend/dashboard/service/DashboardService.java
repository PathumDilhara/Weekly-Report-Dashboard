package com.weeklyreport.backend.dashboard.service;

import com.weeklyreport.backend.dashboard.dto.DashboardSummaryDTO;
import com.weeklyreport.backend.dashboard.dto.ReportStatusDTO;
import com.weeklyreport.backend.dashboard.dto.TrendDTO;
import com.weeklyreport.backend.dashboard.dto.WorkloadDTO;
import com.weeklyreport.backend.exceptions.ServiceUnavailableException;
import com.weeklyreport.backend.project.repo.ProjectRepo;
import com.weeklyreport.backend.report.component.ReportMapper;
import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import com.weeklyreport.backend.report.entity.WeeklyReport;
import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import com.weeklyreport.backend.report.repo.WeeklyReportRepo;
import com.weeklyreport.backend.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.weeklyreport.backend.report.service.WeeklyReportService;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class DashboardService {

    private final WeeklyReportRepo reportRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final ReportMapper reportMapper;

    public DashboardService(
            UserRepo userRepo,
            ProjectRepo projectRepo,
            ReportMapper reportMapper,
            WeeklyReportRepo reportRepo
    ) {
        this.reportRepo = reportRepo;
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
        this.reportMapper = reportMapper;
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
                .map(reportMapper::toDTO)
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



    public List<TrendDTO> getTrends(){
        return reportRepo.getReportTrends();
    }
}