package com.weeklyreport.backend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkloadDTO {
    private String userName;
    private Integer totalHoursWorked;
}
