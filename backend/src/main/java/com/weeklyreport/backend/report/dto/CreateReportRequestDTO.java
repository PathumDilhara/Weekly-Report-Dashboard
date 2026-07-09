package com.weeklyreport.backend.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportRequestDTO {
    private Long projectId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String tasksCompleted;
    private String tasksPlanned;
    private String blockers;
    private Integer hoursWorked;
}
