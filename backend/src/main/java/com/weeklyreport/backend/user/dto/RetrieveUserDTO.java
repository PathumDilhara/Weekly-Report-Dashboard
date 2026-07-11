package com.weeklyreport.backend.user.dto;

import com.weeklyreport.backend.user.enums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveUserDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private TeamEnum team;
}
