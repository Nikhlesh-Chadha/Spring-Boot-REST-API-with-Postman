package com.nikh.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

// we use this class to return data to our front end
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;
}
