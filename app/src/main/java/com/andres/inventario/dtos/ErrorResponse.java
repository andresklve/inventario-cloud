package com.andres.inventario.dtos;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
