package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * This class will intercept any exception mentioned and send response will relevant error code and error message
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * This method is invoked when SignUpRestrictedException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> signUpRestrictedException (SignUpRestrictedException exception, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is invoked when AuthenticationFailedException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException (AuthenticationFailedException exception, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * This method is invoked when AuthenticationFailedException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException (AuthorizationFailedException exception, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.FORBIDDEN);
    }


    /**
     * This method is invoked when AuthenticationFailedException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(UpdateCustomerException.class)
    public ResponseEntity<ErrorResponse> updateCustomerException (UpdateCustomerException exception, WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.BAD_REQUEST);
    }







    /**
     * This method will extract class name from the exception string to avoid exposing entire package name
     * in rest api response
     *
     * @param classname
     * @return
     */
    private String getClassName(String classname) {
        String[] path = classname.split("[.]");
        return path[path.length - 1];
    }
}
