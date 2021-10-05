package com.es.phoneshop.service;

import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    Order getOrderByUUID(String UUID, HttpServletRequest request);
    Order getOrder(Cart cart);
    List<String> getPaymentTypes();
    void placeOrder(Order order);
    void delete(String UUID, HttpServletRequest request);
}
