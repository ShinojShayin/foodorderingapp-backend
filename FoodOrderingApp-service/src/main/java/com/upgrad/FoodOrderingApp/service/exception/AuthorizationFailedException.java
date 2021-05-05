package com.upgrad.FoodOrderingApp.service.exception;

import com.upgrad.FoodOrderingApp.service.common.AuthorizationErrorCode;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * AuthorizationFailedException is thrown when customer is not authorized to access that endpoint.
 */
public class AuthorizationFailedException extends Exception {
    private final String code;
    private final String errorMessage;

    public AuthorizationFailedException(final AuthorizationErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.errorMessage = errorCode.getDefaultMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

