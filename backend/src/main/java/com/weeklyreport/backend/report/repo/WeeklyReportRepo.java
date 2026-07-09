package com.weeklyreport.backend.report.repo;

import com.weeklyreport.backend.report.entity.WeeklyReport;
import com.weeklyreport.backend.report.enums.ReportStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyReportRepo extends JpaRepository<WeeklyReport, Long> {

    // Find WeeklyReports where the report's appUser's userId equals this value
    List<WeeklyReport> findByAppUserUserId(String userId);

    List<WeeklyReport> findByStatus(ReportStatusEnum status);

    long countByStatus(ReportStatusEnum status);
}
