package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.common.AuthenticationErrorCode;
import com.upgrad.FoodOrderingApp.service.common.AuthorizationErrorCode;
import com.upgrad.FoodOrderingApp.service.common.SignupErrorCode;
import com.upgrad.FoodOrderingApp.service.common.UpdateCustomerErrorCode;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

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

        if (StringUtils.isEmpty(reqCustomerEntity.getContactNumber()) ||
                StringUtils.isEmpty(reqCustomerEntity.getEmail()) ||
                StringUtils.isEmpty(reqCustomerEntity.getFirstName()) ||
                StringUtils.isEmpty(reqCustomerEntity.getPassword()))
            throw new SignUpRestrictedException(SignupErrorCode.SGR_005);

        customerService.validateCustomerSignup(reqCustomerEntity);
        CustomerEntity respCustomerEntity = customerService.saveCustomer(reqCustomerEntity);

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

        String[] credentials = null;
        if(Objects.nonNull(authorization) && authorization.startsWith("Basic ")){
            try{
                String decodedAuthStr = new String(Base64.getDecoder().decode(authorization.split("Basic ")[1]));
                credentials = decodedAuthStr.split(":");
                if(credentials.length!=2)
                    throw new AuthenticationFailedException(AuthenticationErrorCode.ATH_003);
            }
            catch (Exception e) {
                throw new AuthenticationFailedException(AuthenticationErrorCode.ATH_003);
            }
        }
        else{
            throw new AuthenticationFailedException(AuthenticationErrorCode.ATH_003);
        }

        CustomerAuthEntity customerAuthEntity = customerService.authenticate(credentials[0],
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

        if(Objects.isNull(authorization) || !authorization.startsWith("Bearer "))
            throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_001);

        String accessToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerService.logout(accessToken);

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

        if(StringUtils.isEmpty(updateCustomerRequest.getFirstName()))
            throw new UpdateCustomerException(UpdateCustomerErrorCode.UCR_002);

        if(Objects.isNull(authorization) || !authorization.startsWith("Bearer "))
            throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_001);

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity loginCustomer = customerService.getCustomer(accessToken);

        loginCustomer.setFirstName(updateCustomerRequest.getFirstName());
        loginCustomer.setLastName(updateCustomerRequest.getLastName());

        CustomerEntity customerEntityResp = customerService.updateCustomer(loginCustomer);

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

        if(StringUtils.isEmpty(updatePasswordRequest.getOldPassword()) || StringUtils.isEmpty(updatePasswordRequest.getNewPassword()))
            throw new UpdateCustomerException(UpdateCustomerErrorCode.UCR_003);

        if(Objects.isNull(authorization) || !authorization.startsWith("Bearer "))
            throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_001);

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity loginCustomer = customerService.getCustomer(accessToken);



        CustomerEntity customerEntityResp = customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword(),
                updatePasswordRequest.getNewPassword(), loginCustomer);

        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse()
                .id(customerEntityResp.getUuid())
                .status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");

        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse,HttpStatus.OK);
    }


}