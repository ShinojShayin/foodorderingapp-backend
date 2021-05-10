package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public OrderEntity insertOrder(OrderEntity orderEntity){
        entityManager.persist(orderEntity);
        return orderEntity;
    }

    public List<OrderEntity> getOrdersByCustomers(CustomerEntity customerEntity) {
        List<OrderEntity> ordersEntities = null;
        try {
            ordersEntities = entityManager.createNamedQuery("getOrdersByCustomers", OrderEntity.class)
                    .setParameter("customer",customerEntity)
                    .getResultList();
        }catch (NoResultException e){
           e.printStackTrace();
        }
        return ordersEntities;
    }

    public List<OrderEntity> getOrdersByRestaurant(RestaurantEntity restaurantEntity){
        List<OrderEntity> ordersEntities = null;
        try{
            ordersEntities = entityManager.createNamedQuery("getOrdersByRestaurant", OrderEntity.class)
                    .setParameter("restaurant",restaurantEntity)
                    .getResultList();
        }catch (NoResultException e){
            e.printStackTrace();
        }
        return ordersEntities;
    }

    public List<OrderEntity> getOrdersByAddress(AddressEntity addressEntity) {
        List<OrderEntity> ordersEntities = null;
        try{
            ordersEntities = entityManager.createNamedQuery("getOrdersByAddress", OrderEntity.class).setParameter("address",addressEntity).getResultList();

        }catch (NoResultException e) {
            e.printStackTrace();
        }
        return ordersEntities;
    }

    public OrderEntity saveOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }
}
