package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    Long save(Order order);
    Order getOrder(Long id);
    void deleteOrder(Long id);
}
