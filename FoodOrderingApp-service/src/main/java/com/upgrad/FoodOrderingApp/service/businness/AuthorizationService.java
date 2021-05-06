package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.AuthorizationErrorCode;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;

@Service
public class AuthorizationService {

    @Autowired
    CustomerAuthDao customerAuthDao;

    public CustomerAuthEntity validateAccessToken(String authorization) throws AuthorizationFailedException {

       if(Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
           String accessToken = authorization.split("Bearer ")[1];

           CustomerAuthEntity customerAuthEntity = customerAuthDao.getCustomerAuthByAccessToken(accessToken);

           if(Objects.isNull(customerAuthEntity))
               throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_001);

           if(Objects.nonNull(customerAuthEntity.getLogoutAt()))
               throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_002);

           if (customerAuthEntity.getExpiresAt().isBefore(ZonedDateTime.now()))
               throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_003);

           return customerAuthEntity;
       }
       else{
           throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_001);
        }

    }
}
