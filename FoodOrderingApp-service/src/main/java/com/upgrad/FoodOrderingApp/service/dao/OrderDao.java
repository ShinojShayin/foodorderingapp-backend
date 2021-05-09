//package com.upgrad.FoodOrderingApp.service.dao;
//
//import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
//import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Repository
//public class OrderDao {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public OrdersEntity getOrdersByAddressId (final Integer address_id){
//        OrdersEntity orders = null;
//        return orders;
//    }
//    public OrdersEntity createOrders(OrdersEntity ordersEntity){
//        entityManager.persist(ordersEntity);
//        return ordersEntity;
//    }
//
//    public OrdersEntity getCustomerByUuid (final String uuid) {
//        OrdersEntity orders = null;
//        return orders;
//    }
//    public OrdersEntity updateCustomer(OrdersEntity orders){
//        entityManager.merge(orders);
//        return orders;
//    }
//}
