package com.bluesalt.challenge.domain.dto;

import lombok.Data;

@Data
public class Response {
    private boolean status;
    private String message;
    private Object data;
}
