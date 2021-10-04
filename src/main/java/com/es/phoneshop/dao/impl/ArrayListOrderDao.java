package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListOrderDao implements OrderDao {

    private static volatile ArrayListOrderDao instance;
    private List<Order> orders;
    public final ReadWriteLock readWriteLock;
    private static Long maxId;

    private ArrayListOrderDao() {
        orders = new ArrayList<Order>();
        this.readWriteLock = new ReentrantReadWriteLock();
        if(maxId == null) {
            maxId = (long) orders.size() + 1;
        }
    }

    public static ArrayListOrderDao getInstance() {
        ArrayListOrderDao result = instance;
        if(result != null) {
            return result;
        }

        synchronized(ArrayListOrderDao.class) {
            if(instance == null) {
                instance = new ArrayListOrderDao();
            }
            return instance;
        }

    }

    @Override
    public Order getOrder(Long id) {
        Order buf;
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }

        try{
            readWriteLock.readLock().lock();
            buf = orders.stream()
                    .filter(o -> id.equals(o.getId()))
                    .findFirst()
                    .orElseThrow(() -> new OrderNotFoundException("No orders with current id were found (id = " + id + ")"));
        } finally {
            readWriteLock.readLock().unlock();
        }

        return buf;
    }

    @Override
    public Long save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order is null");
        }
        List<Order> sameIdOrders;

        try{
            readWriteLock.writeLock().lock();
            if (order.getId() == null){
                order.setId(maxId++);
                orders.add(order);
            } else {
                sameIdOrders = orders.stream()
                        .filter(o -> order.getId().equals(o.getId()))
                        .collect(Collectors.toList());
                if(sameIdOrders.size() < 1){
                    throw new OrderNotFoundException("No orders with current id were found");
                } else {
                    orders.set(orders.indexOf(sameIdOrders.get(0)), order);
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return maxId - 1;
    }

    @Override
    public void deleteOrder(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id is null");
        }

        try {
            readWriteLock.writeLock().lock();
            orders.removeIf(o -> id.equals(o.getId()));
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
