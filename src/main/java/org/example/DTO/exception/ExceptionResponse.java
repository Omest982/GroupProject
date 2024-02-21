package org.example.DTO.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime dateTime;
    private Object payload;
}
