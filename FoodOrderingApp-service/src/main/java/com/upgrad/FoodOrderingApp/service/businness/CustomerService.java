package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.AuthenticationErrorCode;
import com.upgrad.FoodOrderingApp.service.common.SignupErrorCode;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    CustomerAuthDao customerAuthDao;

    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;
    public void customerSignupValidation(CustomerEntity reqCustomerEntity) throws SignUpRestrictedException {

        if (StringUtils.isEmpty(reqCustomerEntity.getContactNumber()) ||
                StringUtils.isEmpty(reqCustomerEntity.getEmail()) ||
                StringUtils.isEmpty(reqCustomerEntity.getFirstName()) ||
                StringUtils.isEmpty(reqCustomerEntity.getPassword()))
            throw new SignUpRestrictedException(SignupErrorCode.SGR_005);

        if (!UtilityProvider.isValidEmail.test(reqCustomerEntity.getEmail()))
            throw new SignUpRestrictedException(SignupErrorCode.SGR_002);

        if (!UtilityProvider.isValidMobileNumber.test(reqCustomerEntity.getContactNumber()))
            throw new SignUpRestrictedException(SignupErrorCode.SGR_003);

        if (!UtilityProvider.isValidPassword.test(reqCustomerEntity.getPassword()))
            throw new SignUpRestrictedException(SignupErrorCode.SGR_004);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity customerSignup(CustomerEntity reqCustomerEntity) throws SignUpRestrictedException {

        CustomerEntity existingCustomer = customerDao.getCustomerByContactNumber(reqCustomerEntity.getContactNumber());

        if(Objects.nonNull(existingCustomer))
            throw new SignUpRestrictedException(SignupErrorCode.SGR_001);

        String[] encryptedPassword = passwordCryptographyProvider.encrypt(reqCustomerEntity.getPassword());
        reqCustomerEntity.setSalt(encryptedPassword[0]);
        reqCustomerEntity.setPassword(encryptedPassword[1]);
        reqCustomerEntity.setUuid(UUID.randomUUID().toString());

        return customerDao.createCustomer(reqCustomerEntity);
    }

    public String[] evaluateAuthorizationHeader(String authorization) throws AuthenticationFailedException {

        String[] credential = null;

        if(StringUtils.isNotEmpty(authorization)){
            try{
                byte[] decodedAuth = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
                String decodedAuthStr = new String(decodedAuth);
                String[] decodedAuthArray = decodedAuthStr.split(":");
                String username = decodedAuthArray[0];
                String password = decodedAuthArray[1];
                return decodedAuthArray;
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        throw new AuthenticationFailedException(AuthenticationErrorCode.ATH_003);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticateCredentials(String contactNumber, String password) throws AuthenticationFailedException {

        CustomerEntity existingCustomer = customerDao.getCustomerByContactNumber(contactNumber);

        if(Objects.isNull(existingCustomer))
            throw new AuthenticationFailedException(AuthenticationErrorCode.ATH_001);

        String encryptedPassword = passwordCryptographyProvider.encrypt(password, existingCustomer.getSalt());

        if (encryptedPassword.equals(existingCustomer.getPassword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
            customerAuthEntity.setCustomer(existingCustomer);

            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(2);

            customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(existingCustomer.getUuid(), now, expiresAt));
            customerAuthEntity.setLoginAt(now);
            customerAuthEntity.setExpiresAt(expiresAt);
            customerAuthEntity.setUuid(UUID.randomUUID().toString());

            CustomerAuthEntity createdCustomerAuthEntity = customerAuthDao.createCustomerAuth(customerAuthEntity);
            return createdCustomerAuthEntity;
        }
        else{
            throw new AuthenticationFailedException(AuthenticationErrorCode.ATH_002);
        }

    }
}