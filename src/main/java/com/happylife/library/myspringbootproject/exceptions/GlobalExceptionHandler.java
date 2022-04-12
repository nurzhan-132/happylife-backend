package com.happylife.library.myspringbootproject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiCallError<Map<String, String>>> handleSQLException(SQLException ex) {
        Map<String, String> details = new HashMap<>();
        if (ex.getErrorCode() == 20201) details.put("cause", "user is already subscribed");
        if (ex.getErrorCode() == 20202) details.put("cause", "user is not a subscriber");
        details.put("code", String.valueOf(ex.getErrorCode()));

        List<Map<String,String>> list = new ArrayList<>();
        list.add(details);

        return ResponseEntity.badRequest()
                .body(new ApiCallError<Map<String, String>>("database error", list));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiCallError<String>>
    handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        return ResponseEntity
            .badRequest()
            .body(new ApiCallError<String>("Missing request parameter", details));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiCallError<T> {

        private String message;
        private List<T> details;

    }
}
