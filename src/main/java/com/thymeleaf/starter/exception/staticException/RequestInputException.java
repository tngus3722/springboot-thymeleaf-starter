package com.thymeleaf.starter.exception.staticException;


import com.thymeleaf.starter.enums.ErrorMessage;
import com.thymeleaf.starter.exception.BaseException;

public class RequestInputException extends BaseException {

    public RequestInputException(String className, ErrorMessage errorMessage, Boolean isCritical) {
        super(className, errorMessage, isCritical);
    }

    public RequestInputException(ErrorMessage errorMessage, Boolean isCritical) {
        super(errorMessage, isCritical);
    }
}