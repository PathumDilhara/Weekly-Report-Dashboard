package com.weeklyreport.backend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDTO {
    private long totalUsers;
    private long totalProjects;
    private long totalReports;
    private long submittedReports;
    private long draftReports;
}
