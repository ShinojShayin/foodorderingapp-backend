package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    
    @RequestMapping(method = RequestMethod.POST, path = "/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signUpCustomer(@RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {

        CustomerEntity reqCustomerEntity = new CustomerEntity();
        reqCustomerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        reqCustomerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        reqCustomerEntity.setFirstName(signupCustomerRequest.getFirstName());
        reqCustomerEntity.setLastName(signupCustomerRequest.getLastName());
        reqCustomerEntity.setPassword(signupCustomerRequest.getPassword());

        customerService.customerSignupValidation(reqCustomerEntity);
        CustomerEntity respCustomerEntity = customerService.customerSignup(reqCustomerEntity);

        SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse()
                .id(respCustomerEntity.getUuid())
                .status("CUSTOMER SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST,path = "/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> customerLogin (@RequestHeader("authorization") final String authorization)throws AuthenticationFailedException {

        String credentials[] = customerService.evaluateAuthorizationHeader(authorization);
        CustomerAuthEntity customerAuthEntity = customerService.authenticateCredentials(credentials[0],
                                                                                        credentials[1]);

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", customerAuthEntity.getAccessToken());

        List<String> header = new ArrayList<>();
        header.add("access-token");
        headers.setAccessControlExposeHeaders(header);

        LoginResponse loginResponse = new LoginResponse()
                .id(customerAuthEntity.getCustomer().getUuid())
                .contactNumber(customerAuthEntity.getCustomer().getContactNumber())
                .emailAddress(customerAuthEntity.getCustomer().getEmail())
                .firstName(customerAuthEntity.getCustomer().getFirstName())
                .lastName(customerAuthEntity.getCustomer().getLastName())
                .message("LOGGED IN SUCCESSFULLY");

        return new ResponseEntity<LoginResponse>(loginResponse,headers,HttpStatus.OK);
    }


}
