package org.example.monitoring_communication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TrackForDeviceNotFoundAdvise {
    @ResponseBody
    @ExceptionHandler(TrackForDeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> exceptionHandler(TrackForDeviceNotFoundException exception)
    {
        Map<String,String> response = new HashMap<>();
        response.put("errorMessage",exception.getMessage());
        return response;
    }
}
