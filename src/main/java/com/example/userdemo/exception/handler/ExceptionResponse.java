package com.example.userdemo.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * An encapsulation of error information to be returned as part of an HTTP
 * response in the event of an exception.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String message;

    private String timeStamp;

    /**
     * Constructs an {@code ExceptionResponse} object using error attributes
     * obtained from a Spring WebRequest.
     *
     * @param errorAttributes A map of error attributes, obtained from a Spring
     *                        WebRequest, containing error information.
     */
    public ExceptionResponse(Map<String, Object> errorAttributes) {
        this.setMessage((String) errorAttributes.get("message"));
        this.setTimeStamp(errorAttributes.get("timestamp").toString());
    }
}
