package com.weeklyreport.backend.report.controller;

import com.weeklyreport.backend.report.dto.CreateReportRequestDTO;
import com.weeklyreport.backend.report.dto.ReportResponseDTO;
import com.weeklyreport.backend.report.dto.UpdateReportRequestDTO;
import com.weeklyreport.backend.report.service.WeeklyReportService;
import com.weeklyreport.backend.response.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/report")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @PostMapping
    public CustomResponse<ReportResponseDTO> createReport(
            @Valid @RequestBody CreateReportRequestDTO dto) {

        ReportResponseDTO res = weeklyReportService.createReport(dto);

        return new CustomResponse<>(true,"Report created successfully", res);
    }

    @PutMapping("/{reportId}")
    public CustomResponse<ReportResponseDTO> updateReport(
            @PathVariable Long reportId,
            @Valid @RequestBody UpdateReportRequestDTO request) {

        ReportResponseDTO res = weeklyReportService.updateReport(reportId, request);

        return new CustomResponse<>(true,"Report Updated Successful", res);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long reportId) {

        weeklyReportService.deleteReport(reportId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reportId}")
    public CustomResponse<ReportResponseDTO> getReportById(
            @PathVariable Long reportId) {

        ReportResponseDTO res = weeklyReportService.getReportById(reportId);
        return new CustomResponse<>(true, "Report fetched successfully : " + reportId, res);
    }

//    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/my")
    public CustomResponse<List<ReportResponseDTO>> getMyReports() {
        List<ReportResponseDTO> list = weeklyReportService.getMyReports();
        return new CustomResponse<>(true,"All report fetched", list);
    }

    @PostMapping("/{reportId}/submit")
    public CustomResponse<ReportResponseDTO> submitReport(
            @PathVariable Long reportId) {
        ReportResponseDTO res = weeklyReportService.submitReport(reportId);
        return new CustomResponse<>(true, "Report submitted successfully", res);
    }
}
