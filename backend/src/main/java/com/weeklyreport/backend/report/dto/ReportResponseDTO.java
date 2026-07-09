package com.weeklyreport.backend.report.dto;

import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {
    private Long id;

    // Project details
    private Long projectId;
    private String projectName;

    // User details
    private String userId;
    private String userName;
    private String userEmail;

    // Report details
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String tasksCompleted;
    private String tasksPlanned;
    private String blockers;
    private Integer hoursWorked;
    private ReportStatusEnum status;
    private LocalDateTime submittedAt;

    // Audit fields
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
