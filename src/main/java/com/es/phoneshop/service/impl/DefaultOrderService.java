package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.validator.PaymentTypeResolver;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.es.phoneshop.constant.ConstantStrings.STRING_SESSION_ATTRIBUTE_ORDER;

public class DefaultOrderService implements OrderService {

    private static volatile DefaultOrderService instance;

    private OrderDao orderDao;

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
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
    public Order getOrderByUUID(String UUID, HttpServletRequest request) {
        List<Order> orders = (List<Order>)request.getSession().getAttribute(STRING_SESSION_ATTRIBUTE_ORDER);
        return orders.stream()
                .filter(o -> UUID.equals(o.getSecureId()))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order with current id is not found"));
    }

    //Forming order from given cart
    @Override
    public Order getOrder(Cart cart) {
        Order order;
        try {
            order = new Order(cart.clone());
        } catch (CloneNotSupportedException e) {
            return null;
        }
        order.setSubTotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost(cart));
        order.setTotalCost(order.getSubTotal().add(order.getDeliveryCost()));
        return order;
    }

    @Override
    public List<String> getPaymentTypes() {
        return Arrays.stream(PaymentMethodType.values())
                .map(PaymentTypeResolver::GetMessageFromType)
                .collect(Collectors.toList());
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    @Override
    public void delete(String UUID, HttpServletRequest request) {
        List<Order> orders = (List<Order>)request.getSession().getAttribute(STRING_SESSION_ATTRIBUTE_ORDER);
        Order order = getOrderByUUID(UUID,request);
        orders.remove(order);
        request.getSession().setAttribute(STRING_SESSION_ATTRIBUTE_ORDER, orders);
    }

    private BigDecimal calculateDeliveryCost(Cart cart){
        if(cart.getTotalCost().compareTo(new BigDecimal(500)) < 0){
            return new BigDecimal(10);
        } else {
            return new BigDecimal(0);
        }
    }
}
