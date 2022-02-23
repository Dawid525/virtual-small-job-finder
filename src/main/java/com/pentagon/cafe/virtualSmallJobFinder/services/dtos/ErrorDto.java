package com.pentagon.cafe.virtualSmallJobFinder.services.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Data
@ToString
public class ErrorDto {
    private String errorMessage;
    private String errorCode;
}
