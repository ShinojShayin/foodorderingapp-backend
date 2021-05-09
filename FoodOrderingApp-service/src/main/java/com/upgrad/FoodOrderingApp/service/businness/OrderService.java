//package com.upgrad.FoodOrderingApp.service.businness;
//
//import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
//import com.upgrad.FoodOrderingApp.service.entity.OrdersEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class OrderService {
//
//    @Autowired
//    private OrderDao orderDao;
//
//    @Transactional(propagation = Propagation.MANDATORY)
//    public OrdersEntity createOrders(OrdersEntity ordersEntity){
//        return orderDao.createOrders(ordersEntity);
//    }
//
//}
