package com.es.phoneshop.dao.impl;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import org.junit.Before;
import org.junit.Test;

import static com.es.phoneshop.constant.ConstantStrings.ID_IS_NULL;
import static com.es.phoneshop.constant.ConstantStrings.NO_ORDERS_FOUND;
import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {

    private ArrayListOrderDao dao;

    @Before
    public void setup(){
        dao = ArrayListOrderDao.getInstance();
    }

    @Test
    public void testSaveGetAndDeleteOrder(){
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Order order4 = new Order();
        Order order;

        try{
            order = dao.getOrder(null);
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_IS_NULL, ex.getMessage());
        }

        Order bufOrder = new Order();
        dao.save(bufOrder);

        try{
            order = dao.getOrder(bufOrder.getId() + 1);
            fail();
        } catch (OrderNotFoundException ex){
            assertEquals(String.format(NO_ORDERS_FOUND, bufOrder.getId() + 1), ex.getMessage());
        }

        order1.setPhone("1414141414");
        order2.setPhone("85858585585");
        order3.setPhone("55555555555");
        order4.setPhone("44444444444");
        dao.save(order1);
        long id1 = order1.getId();
        dao.save(order2);
        long id2 = order2.getId();
        dao.save(order3);
        long id3 = order3.getId();
        order4.setId(id3);
        dao.save(order4);
        long id4 = order4.getId();

        assertEquals(dao.getOrder(id2), order2);
        assertEquals(dao.getOrder(id1), order1);
        assertEquals(dao.getOrder(id3), order4);
        assertEquals(dao.getOrder(id3).getPhone(), "44444444444");
        assertNotEquals(dao.getOrder(id1), order2);
        assertNotEquals(dao.getOrder(id2), order1);
        assertNotEquals(dao.getOrder(id3), order3);

        dao.deleteOrder(id3);
        dao.deleteOrder(id2);
        dao.deleteOrder(id1);

        try{
            dao.deleteOrder(null);
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_IS_NULL, ex.getMessage());
        }

        try{
            order = dao.getOrder(id3);
            fail();
        } catch (OrderNotFoundException ex){
            assertEquals(String.format(NO_ORDERS_FOUND, id3), ex.getMessage());
        }

        try{
            order = dao.getOrder(id2);
            fail();
        } catch (OrderNotFoundException ex){
            assertEquals(String.format(NO_ORDERS_FOUND, id2), ex.getMessage());
        }

        try{
            order = dao.getOrder(id1);
            fail();
        } catch (OrderNotFoundException ex){
            assertEquals(String.format(NO_ORDERS_FOUND, id1), ex.getMessage());
        }

    }

}
