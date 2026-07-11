package com.weeklyreport.backend.report.dto;

import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import com.weeklyreport.backend.user.enums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {
    private Long reportId;

    // Project details
    private Long projectId;
    private String projectName;

    // User details
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private TeamEnum team;

    // Report details
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String tasksCompleted;
    private String tasksPlanned;
    private String blockers;
    private Integer hoursWorked;
    private ReportStatusEnum status;
    private LocalDateTime submittedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
