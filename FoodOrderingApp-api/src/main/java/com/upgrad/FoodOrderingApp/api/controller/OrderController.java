//package com.upgrad.FoodOrderingApp.api.controller;
//
//import com.upgrad.FoodOrderingApp.api.model.*;
//import com.upgrad.FoodOrderingApp.service.common.AuthorizationErrorCode;
//import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
//import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
//import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Objects;
//
//@RestController
//@CrossOrigin
//public class OrderController {
//
////    public ResponseEntity<CouponDetailsResponse> couponResponse(
////            @RequestBody (required = false) final CouponDetailsRequest couponDetailsRequest)
////            throws AuthorizationFailedException {
////        if(Objects.isNull(authorization) || !authorization.startsWith("Bearer "))
////            throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_001);
////
////
////        String accessToken = authorization.split("Bearer ")[1];
////        CustomerEntity loginCustomer = customerService.getCustomer(accessToken);
////        OrdersEntity reqOrdersEntity = new OrdersEntity();
////
////        reqOrdersEntity.setDiscount(couponDetailsResponse.getPercent());
////        reqOrdersEntity.setCustomer_id(couponDetailsResponse.);
////
////
////        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
////    }
////
////
////    public ResponseEntity<CustomerOrderResponse> pastOrder(final CustomerOrderResponse customerOrderResponse) {
////        return new ResponseEntity<CustomerOrderResponse>(customerOrderResponse, HttpStatus.OK);
////    }
////
//////    @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//////    public ResponseEntity<SaveOrderRequest> saveOrder(final SaveOrderRequest saveOrderRequest) {
//////        return new ResponseEntity<SaveOrderResponse>(saveOrderRequest, HttpStatus.CREATED);
//////    }
//}
//
//
//
//
//
//
