package com.pentagon.cafe.virtualSmallJobFinder.payload;

import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import lombok.Data;

@Data
public class TypeRequest {
    private UserType type;
}
