package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;

public class DefaultOrderService implements OrderService {

    private static volatile DefaultOrderService instance;

    private DefaultOrderService() {

    }

    public static DefaultOrderService getInstance() {
        DefaultOrderService result = instance;
        if(result != null) {
            return result;
        }

        synchronized(DefaultOrderService.class) {
            if(instance == null) {
                instance = new DefaultOrderService();
            }
            return instance;
        }
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order(cart);
        order.setSubTotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubTotal().add(order.getDeliveryCost()));
        return order;
    }

    private BigDecimal calculateDeliveryCost(){
        return new BigDecimal(10);
    }
}
