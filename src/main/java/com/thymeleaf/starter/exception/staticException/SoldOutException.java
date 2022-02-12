package com.thymeleaf.starter.exception.staticException;

import com.thymeleaf.starter.enums.ErrorMessage;
import com.thymeleaf.starter.exception.BaseException;

public class SoldOutException extends BaseException {

    public SoldOutException(String className, ErrorMessage errorMessage, Boolean isCritical) {
        super(className, errorMessage, isCritical);
    }

    public SoldOutException(ErrorMessage errorMessage, Boolean isCritical) {
        super(errorMessage, isCritical);
    }
}
