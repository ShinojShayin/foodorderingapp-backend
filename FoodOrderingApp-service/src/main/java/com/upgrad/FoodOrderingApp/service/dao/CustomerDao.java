package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerEntity getCustomerByContactNumber (final String contact_number){
        CustomerEntity customer = null;
        try{
            customer = entityManager.createNamedQuery("customerByContactNumber",CustomerEntity.class)
                    .setParameter("contact_number",contact_number).getSingleResult();

        }catch (NoResultException e){
           e.printStackTrace();
        }

        return customer;
    }

    public CustomerEntity createCustomer(CustomerEntity customerEntity){
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    public CustomerEntity getCustomerByUuid (final String uuid){
        CustomerEntity customer = null;
        try {
            customer = entityManager.createNamedQuery("customerByUuid",CustomerEntity.class).setParameter("uuid",uuid).getSingleResult();

        }catch (NoResultException e){
           e.printStackTrace();
        }
        return customer;
    }

    public CustomerEntity updateCustomer(CustomerEntity customer){
        entityManager.merge(customer);
        return customer;
    }
}
