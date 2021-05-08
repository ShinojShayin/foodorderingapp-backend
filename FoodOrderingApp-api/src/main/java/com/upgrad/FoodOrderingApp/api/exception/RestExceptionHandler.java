package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.*;
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
    public ResponseEntity<ErrorResponse> signUpRestrictedException (SignUpRestrictedException exception,
                                                                    WebRequest request){
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
    public ResponseEntity<ErrorResponse> authenticationFailedException (AuthenticationFailedException exception,
                                                                        WebRequest request){
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
    public ResponseEntity<ErrorResponse> authorizationFailedException (AuthorizationFailedException exception,
                                                                       WebRequest request){
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
    public ResponseEntity<ErrorResponse> updateCustomerException (UpdateCustomerException exception,
                                                                  WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is invoked when AddressNotFoundException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> addressNotFoundException (AddressNotFoundException exception,
                                                                   WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method is invoked when CategoryNotFoundException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> categoryNotFoundException (CategoryNotFoundException exception,
                                                                   WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method is invoked when CouponNotFoundException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorResponse> couponNotFoundException (CouponNotFoundException exception,
                                                                   WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method is invoked when InvalidRatingException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(InvalidRatingException.class)
    public ResponseEntity<ErrorResponse> invalidRatingException (InvalidRatingException exception,
                                                                  WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is invoked when ItemNotFoundException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> itemNotFoundException (ItemNotFoundException exception,
                                                                 WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method is invoked when PaymentMethodNotFoundException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<ErrorResponse> paymentMethodNotFoundException (PaymentMethodNotFoundException exception,
                                                                WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method is invoked when RestaurantNotFoundException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> restaurantNotFoundException (RestaurantNotFoundException exception,
                                                                         WebRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse()
                .code(exception.getCode())
                .message(exception.getErrorMessage())
                .rootCause(getClassName(exception.toString())),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method is invoked when SaveAddressException thrown and relevant error code and error message
     *
     * @param exception
     * @param request
     * @return ErrorResponse
     */
    @ExceptionHandler(SaveAddressException.class)
    public ResponseEntity<ErrorResponse> saveAddressException (SaveAddressException exception,
                                                                      WebRequest request){
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
