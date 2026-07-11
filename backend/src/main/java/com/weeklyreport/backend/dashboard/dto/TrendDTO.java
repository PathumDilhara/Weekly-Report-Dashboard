package com.weeklyreport.backend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendDTO {
    private String week;
    private Long reportCount;
}
