package com.bluesalt.challenge.utils;

import com.bluesalt.challenge.domain.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseUtils {
    public ResponseEntity<Response> getResponse(boolean status, String message){
        Response response = new Response();
        response.setStatus(true);
        response.setMessage(message);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getResponse(boolean status, String message, Object data){
        Response response = new Response();
        response.setStatus(true);
        response.setMessage(message);
        response.setData(data);
        return ResponseEntity.ok(response);
    }
}
