package com.upgrad.FoodOrderingApp.service.exception;

import com.upgrad.FoodOrderingApp.service.common.AuthenticationErrorCode;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * AuthenticationFailedException is thrown when customer credentials didn't match.
 */
public class AuthenticationFailedException extends Exception {
    private final String code;
    private final String errorMessage;

    public AuthenticationFailedException(AuthenticationErrorCode errorCode) {
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
