package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerAddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity){
        entityManager.persist(customerAddressEntity);
        return customerAddressEntity;
    }

    public List<CustomerAddressEntity> getAllCustomerAddressByCustomer(CustomerEntity customerEntity) {
        List <CustomerAddressEntity> customerAddressEntities = null;
        try{

            customerAddressEntities = entityManager.createNamedQuery("getAllCustomerAddressByCustomer",
                    CustomerAddressEntity.class).setParameter("customer_entity",customerEntity)
                    .setParameter("active",1).getResultList();

        }catch (NoResultException e){
            e.printStackTrace();
        }
        return customerAddressEntities;
    }

    public CustomerAddressEntity getCustomerAddressByAddress(AddressEntity addressEntity){
        CustomerAddressEntity customerAddressEntity = null;
        try {
             customerAddressEntity = entityManager.createNamedQuery("getCustomerAddressByAddress",CustomerAddressEntity.class).setParameter("address_entity",addressEntity).getSingleResult();
        }catch (NoResultException e){
           e.printStackTrace();
        }
        return customerAddressEntity;
    }
}
