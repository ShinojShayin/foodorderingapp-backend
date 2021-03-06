package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PaymentDao {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * This method fetch payment details by uuid
     *
     * @param paymentId
     * @return
     */
    public PaymentEntity getPaymentByUUID(String paymentId) {
        try {
            PaymentEntity paymentEntity = entityManager.createNamedQuery("getPaymentByUUID", PaymentEntity.class)
                    .setParameter("uuid", paymentId).getSingleResult();
            return paymentEntity;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * This method fetch all payment methods from database
     *
     * @return
     */
    public List<PaymentEntity> getAllPaymentMethods() {
        try {
            List<PaymentEntity> paymentEntities = entityManager.createNamedQuery("getAllPaymentMethods", PaymentEntity.class).getResultList();
            return paymentEntities;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
