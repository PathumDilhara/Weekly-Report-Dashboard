package com.weeklyreport.backend.dashboard.dto;

import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportStatusDTO {
    private String userName;
    private String projectName;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private ReportStatusEnum status;
}
