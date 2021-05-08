package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.common.AddressNotFoundErrorCode;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;


    public AddressEntity saveAddress(AddressEntity addressEntity){
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    public AddressEntity getAddressByUuid(String uuid){
        AddressEntity addressEntity = null;
        try{
            addressEntity = entityManager.createNamedQuery("getAddressByUuid",AddressEntity.class).setParameter("uuid",uuid).getSingleResult();

        }catch (NoResultException e){
            e.printStackTrace();
        }
        return addressEntity;
    }

    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        entityManager.remove(addressEntity);
        return addressEntity;
    }
}
