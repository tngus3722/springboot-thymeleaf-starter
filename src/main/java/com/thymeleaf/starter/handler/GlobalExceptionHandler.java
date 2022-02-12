package com.thymeleaf.starter.handler;

import com.thymeleaf.starter.exception.BaseException;
import com.thymeleaf.starter.util.exception.ExceptionParser;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionParser exceptionParser;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseException> defaultException(Throwable e) throws IOException {
        BaseException baseException = exceptionParser.baseExceptionParser(e);
        return new ResponseEntity<>(baseException, baseException.getHttpStatus());
    }
}
