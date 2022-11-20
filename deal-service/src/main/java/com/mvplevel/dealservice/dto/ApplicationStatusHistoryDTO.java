package com.mvplevel.dealservice.dto;

import com.mvplevel.dealservice.constants.ApplicationStatus;
import com.mvplevel.dealservice.constants.ChangeType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationStatusHistoryDTO {
    private ApplicationStatus applicationStatus;
    private LocalDate time;
    private ChangeType changeType;
}
