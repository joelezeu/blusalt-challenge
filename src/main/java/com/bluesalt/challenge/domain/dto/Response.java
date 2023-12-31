package com.bluesalt.challenge.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private boolean status;
    private String message;
    private Object data;
}
