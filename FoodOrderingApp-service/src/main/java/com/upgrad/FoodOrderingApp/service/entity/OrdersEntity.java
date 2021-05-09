//package com.upgrad.FoodOrderingApp.service.entity;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.util.Date;
//
//@Entity
//@Table(name="orders",uniqueConstraints = {@UniqueConstraint(columnNames = {"uuid","address_id"})})
//@NamedQueries(
//        {   @NamedQuery(name = "getOrdersByCustomers",query = "SELECT o FROM OrdersEntity o WHERE o.customer = :customer ORDER BY o.date DESC "),
//                @NamedQuery(name = "getOrdersByRestaurant",query = "SELECT o FROM OrdersEntity o WHERE o.restaurant = :restaurant"),
//                @NamedQuery(name = "getOrdersByAddress",query = "SELECT o FROM OrdersEntity o WHERE o.address = :address")
//        }
//)
//public class OrdersEntity {
//
//        @Id
//        @Column(name = "id")
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Integer id;
//
//
//        @Column(name = "uuid")
//        @Size(max = 200)
//        @NotNull
//        private String uuid;
//
//        @Column(name="bill")
//        @NotNull
//        private Integer bill;
//
//        @Column(name = "coupon_id")
//        private Integer coupon_id;
//
//        @Column(name = "discount")
//        private Integer discount;
//
//        @Column(name = "date")
//        @NotNull
//        private Date date;
//
//        @Column(name = "payment_id")
//        private Integer payment_id;
//
//        @Column(name = "customer_id")
//        @NotNull
//        private Integer customer_id;
//
//        @Column(name = "address_id")
//        @NotNull
//        private Integer address_id;
//
//        @Column(name = "restaurant_id")
//        @NotNull
//        private Integer restaurant_id;
//
//        public Integer getId() {
//                return id;
//        }
//
//        public void setId(Integer id) {
//                this.id = id;
//        }
//
//        public String getUuid() {
//                return uuid;
//        }
//
//        public void setUuid(String uuid) {
//                this.uuid = uuid;
//        }
//
//        public Integer getBill() {
//                return bill;
//        }
//
//        public void setBill(Integer bill) {
//                this.bill = bill;
//        }
//
//        public Integer getCoupon_id() {
//                return coupon_id;
//        }
//
//        public void setCoupon_id(Integer coupon_id) {
//                this.coupon_id = coupon_id;
//        }
//
//        public Integer getDiscount() {
//                return discount;
//        }
//
//        public void setDiscount(Integer discount) {
//                this.discount = discount;
//        }
//
//        public Date getDate() {
//                return date;
//        }
//
//        public void setDate(Date date) {
//                this.date = date;
//        }
//
//        public Integer getPayment_id() {
//                return payment_id;
//        }
//
//        public void setPayment_id(Integer payment_id) {
//                this.payment_id = payment_id;
//        }
//
//        public Integer getCustomer_id() {
//                return customer_id;
//        }
//
//        public void setCustomer_id(Integer customer_id) {
//                this.customer_id = customer_id;
//        }
//
//        public Integer getAddress_id() {
//                return address_id;
//        }
//
//        public void setAddress_id(Integer address_id) {
//                this.address_id = address_id;
//        }
//
//        public Integer getRestaurant_id() {
//                return restaurant_id;
//        }
//
//        public void setRestaurant_id(Integer restaurant_id) {
//                this.restaurant_id = restaurant_id;
//        }
//
//        @Override
//        public String toString() {
//                return "OrdersEntity{" +
//                        "id=" + id +
//                        ", uuid='" + uuid + '\'' +
//                        ", bill='" + bill + '\'' +
//                        ", coupon_id='" + coupon_id + '\'' +
//                        ", discount ='" + discount + '\''+
//                        ", date='" + date + '\'' +
//                        ", payment_id='" + payment_id + '\'' +
//                        ", customer_id='" + customer_id + '\'' +
//                        ", address_id='" + address_id + '\'' +
//                        ", restaurant_id='" + restaurant_id + '\'' +
//                        '}';
//        }
//}
