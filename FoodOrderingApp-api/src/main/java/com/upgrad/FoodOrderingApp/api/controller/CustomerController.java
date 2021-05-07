package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AuthorizationService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    AuthorizationService authorizationService;


    @RequestMapping(method = RequestMethod.POST, path = "/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signUpCustomer(
            @RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest)
            throws SignUpRestrictedException {

        CustomerEntity reqCustomerEntity = new CustomerEntity();
        reqCustomerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        reqCustomerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        reqCustomerEntity.setFirstName(signupCustomerRequest.getFirstName());
        reqCustomerEntity.setLastName(signupCustomerRequest.getLastName());
        reqCustomerEntity.setPassword(signupCustomerRequest.getPassword());

        customerService.validateCustomerSignup(reqCustomerEntity);
        CustomerEntity respCustomerEntity = customerService.customerSignup(reqCustomerEntity);

        SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse()
                .id(respCustomerEntity.getUuid())
                .status("CUSTOMER SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> loginCustomer(
            @RequestHeader("authorization") final String authorization)
            throws AuthenticationFailedException {

        String[] credentials = customerService.validateLoginAuthorizationHeader(authorization);
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

        return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logoutCustomer(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        CustomerAuthEntity loggedCustomerAuth = authorizationService.validateAccessToken(authorization);

        CustomerAuthEntity customerAuthEntity = customerService.logout(loggedCustomerAuth.getAccessToken());

        LogoutResponse logoutResponse = new LogoutResponse()
                .id(customerAuthEntity.getCustomer().getUuid())
                .message("LOGGED OUT SUCCESSFULLY");
        return new ResponseEntity<LogoutResponse>(logoutResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomerDetails(
            @RequestHeader("authorization") final String authorization,
            @RequestBody(required = false) UpdateCustomerRequest updateCustomerRequest)
            throws AuthorizationFailedException, UpdateCustomerException {

        CustomerAuthEntity loggedCustomerAuth = authorizationService.validateAccessToken(authorization);

        CustomerEntity customerEntityReq = loggedCustomerAuth.getCustomer();
        customerEntityReq.setFirstName(updateCustomerRequest.getFirstName());
        customerEntityReq.setLastName(updateCustomerRequest.getLastName());

        customerService.validateUpdateCustomer(customerEntityReq);

        CustomerEntity customerEntityResp = customerService.updateCustomerEntity(customerEntityReq);

        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse()
                .firstName(customerEntityResp.getFirstName())
                .lastName(customerEntityResp.getLastName())
                .id(customerEntityResp.getUuid())
                .status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");

        return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/password",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updateCustomerPassword(
            @RequestHeader ("authorization") final String authorization,
            @RequestBody(required = false) UpdatePasswordRequest updatePasswordRequest)
            throws AuthorizationFailedException,UpdateCustomerException {

        CustomerAuthEntity loggedCustomerAuth = authorizationService.validateAccessToken(authorization);

        CustomerEntity customerEntityResp = customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword(),
                updatePasswordRequest.getNewPassword(), loggedCustomerAuth.getCustomer());

        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse()
                .id(customerEntityResp.getUuid())
                .status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");

        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse,HttpStatus.OK);
    }


}