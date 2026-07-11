package com.weeklyreport.backend.report.component;

import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import com.weeklyreport.backend.report.entity.WeeklyReport;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportResponseDTO toDTO(WeeklyReport report) {
        if (report == null) return null;

        ReportResponseDTO dto = new ReportResponseDTO();

        dto.setReportId(report.getReportId());

        // Project details
        if (report.getProject() != null) {
            dto.setProjectId(report.getProject().getId());
            dto.setProjectName(report.getProject().getName());
        }

        // User details
        if (report.getAppUser() != null) {
            dto.setUserId(report.getAppUser().getUserId());
            dto.setFirstName(report.getAppUser().getFirstName());
            dto.setLastName(report.getAppUser().getLastName());
            dto.setEmail(report.getAppUser().getEmail());
            dto.setTeam(report.getAppUser().getTeam());
        }

        // Report details
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