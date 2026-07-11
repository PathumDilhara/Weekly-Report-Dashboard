package com.weeklyreport.backend.dashboard.controller;

import com.weeklyreport.backend.dashboard.dto.DashboardSummaryDTO;
import com.weeklyreport.backend.dashboard.dto.ReportStatusDTO;
import com.weeklyreport.backend.dashboard.dto.WorkloadDTO;
import com.weeklyreport.backend.dashboard.service.DashboardService;
import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import com.weeklyreport.backend.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1/dashboard")
@PreAuthorize("hasRole('MANAGER')")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public CustomResponse<DashboardSummaryDTO> getSummary() {
        DashboardSummaryDTO res =  dashboardService.getSummary();
        return new CustomResponse<>(true, "Summary fetched successfully", res);
    }

    @GetMapping("/reports")
    public CustomResponse<List<ReportResponseDTO>> getAllReports() {
        List<ReportResponseDTO> res = dashboardService.getAllReports();
        return new CustomResponse<>(true, "All reports fetched successfully", res);
    }

    @GetMapping("/submission-status")
    public CustomResponse<List<ReportStatusDTO>> getSubmissionStatus() {
        List<ReportStatusDTO> res = dashboardService.getSubmissionStatus();
        return new CustomResponse<>(true, "Submission state fetched successfully", res);
    }

    @GetMapping("/workload")
    public CustomResponse<List<WorkloadDTO>> getWorkload() {
        List<WorkloadDTO> res = dashboardService.getWorkload();
        return new CustomResponse<>(true, "Workload fetched successfully", res);
    }

    @GetMapping("/trends")
    public CustomResponse<?> getTrends(){

        return new CustomResponse<>(true, "Trend fetched successfully",
                dashboardService.getTrends()
        );
    }
}
