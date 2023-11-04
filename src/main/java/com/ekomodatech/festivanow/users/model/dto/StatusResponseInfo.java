package com.ekomodatech.festivanow.users.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class StatusResponseInfo {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
}
