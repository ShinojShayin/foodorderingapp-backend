package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.common.AddressNotFoundErrorCode;
import com.upgrad.FoodOrderingApp.service.common.AuthorizationErrorCode;
import com.upgrad.FoodOrderingApp.service.common.SaveAddressErrorCode;
import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressService {

    @Autowired
    StateDao stateDao;

    @Autowired
    CustomerAddressDao customerAddressDao;

    @Autowired
    AddressDao addressDao;

    public StateEntity getStateByUUID (String uuid)throws AddressNotFoundException {
        StateEntity stateEntity = stateDao.getStateByUuid(uuid);
        if(stateEntity == null)
            throw new AddressNotFoundException(AddressNotFoundErrorCode.ANF_002);

        return  stateEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity,StateEntity stateEntity)throws SaveAddressException {

        if ( StringUtils.isEmpty(addressEntity.getCity()) || StringUtils.isEmpty(addressEntity.getFlatBuilNo())
                || StringUtils.isEmpty(addressEntity.getPincode())
                || StringUtils.isEmpty( addressEntity.getLocality()))
            throw new SaveAddressException(SaveAddressErrorCode.SAR_001);

        if(!UtilityProvider.isPincodeValid(addressEntity.getPincode()))
            throw new SaveAddressException(SaveAddressErrorCode.SAR_002);

        addressEntity.setState(stateEntity);

        AddressEntity savedAddress = addressDao.saveAddress(addressEntity);

        //returning SavedAddress
        return savedAddress;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAddressEntity saveCustomerAddressEntity(CustomerEntity customerEntity,AddressEntity addressEntity){

        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setCustomer(customerEntity);
        customerAddressEntity.setAddress(addressEntity);

        CustomerAddressEntity createdCustomerAddressEntity = customerAddressDao.saveCustomerAddress(customerAddressEntity);
        return createdCustomerAddressEntity;

    }

    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {

        List<CustomerAddressEntity> customerAddressEntities  = customerAddressDao.getAllCustomerAddressByCustomer(customerEntity);

        List<AddressEntity> addressEntities = new LinkedList<>();
        if(Objects.nonNull(customerAddressEntities)){
            customerAddressEntities.stream().forEach(obj->{
                addressEntities.add(obj.getAddress());
            });
        }
        Collections.reverse(addressEntities);
        return addressEntities;
    }

    public AddressEntity getAddressByUUID(String addressUuid, CustomerEntity customerEntity) throws AddressNotFoundException, AuthorizationFailedException {

        if(Objects.isNull(addressUuid))
            throw new AddressNotFoundException(AddressNotFoundErrorCode.ANF_005);

        AddressEntity addressEntity = addressDao.getAddressByUuid(addressUuid);

        if(Objects.isNull(addressEntity))
            throw new AddressNotFoundException(AddressNotFoundErrorCode.ANF_003);

        CustomerAddressEntity customerAddressEntity = customerAddressDao.getCustomerAddressByAddress(addressEntity);

        if(Objects.isNull(customerAddressEntity) ||
                !(customerAddressEntity.getCustomer().getUuid() == customerEntity.getUuid()))
            throw new AuthorizationFailedException(AuthorizationErrorCode.ATHR_004);

        return addressEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteAddress(AddressEntity addressEntity) {

        AddressEntity deletedAddressEntity = addressDao.deleteAddress(addressEntity);
        return deletedAddressEntity;

    }

    public List<StateEntity> getAllStates() {
        List<StateEntity> stateEntities = stateDao.getAllStates();
        return stateEntities;
    }
}
