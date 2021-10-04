package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.validator.PaymentTypeResolver;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        order.setDeliveryCost(calculateDeliveryCost(cart));
        order.setTotalCost(order.getSubTotal().add(order.getDeliveryCost()));
        return order;
    }

    @Override
    public List<String> getPaymentTypes() {
        return Arrays.asList(PaymentMethodType.values())
                .stream()
                .map(PaymentTypeResolver::GetMessageFromType)
                .collect(Collectors.toList());
    }

    private BigDecimal calculateDeliveryCost(Cart cart){
        if(cart.getTotalCost().compareTo(new BigDecimal(500)) == -1){
            return new BigDecimal(10);
        } else {
            return new BigDecimal(0);
        }

    }
}
