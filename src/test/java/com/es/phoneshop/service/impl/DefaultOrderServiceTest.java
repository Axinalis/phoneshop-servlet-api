package com.es.phoneshop.service.impl;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultOrderServiceTest {

	@Mock
	private HttpServletRequest request;
	@InjectMocks
	private HttpSession session;

	private DefaultOrderService service;
	private List<Order> orders;
	private List<String> uuids;
	private Cart cart;
	private OrderDao dao = ArrayListOrderDao.getInstance();
	
	@Before
	public void setup() { 
		service = DefaultOrderService.getInstance();
		orders = new ArrayList<>();
		uuids = new ArrayList<>();
		cart = new Cart();
		cart.getItems().add(new CartItem(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "/Samsung/Samsung%20Galaxy%20S.jpg"), 5));
		cart.getItems().add(new CartItem(new Product(2L, "sgs1", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "/Samsung/Samsung%20Galaxy%20S.jpg"), 50));
		cart.getItems().add(new CartItem(new Product(3L, "sgs11", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "/Samsung/Samsung%20Galaxy%20S.jpg"), 56));
		cart.getItems().add(new CartItem(new Product(4L, "sgs111", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "/Samsung/Samsung%20Galaxy%20S.jpg"), 15));
		for(int i=0;i<5;i++){
			addRandomOrder();
		}
		request = mock(HttpServletRequest.class);
		session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute(ConstantStrings.STRING_SESSION_ATTRIBUTE_ORDER)).thenReturn(orders);
	}

	private void addRandomOrder(){
		String uuid = UUID.randomUUID().toString();
		Order bufOrder = new Order();
		bufOrder.setSecureId(uuid);
		orders.add(bufOrder);
		uuids.add(uuid);
	}
	
	@Test
	public void testGettingByUUID() {
		Order order = service.getOrderByUUID(uuids.get(0), request);
		assertEquals(order, orders.get(0));
		assertNotEquals(order, orders.get(1));

		try{
			order = service.getOrderByUUID(UUID.randomUUID().toString(), request);
			fail();
		} catch(OrderNotFoundException ignored){

		}
	}

	@Test
	public void testCreatingOrder(){
		cart.setTotalCost(new BigDecimal(5));
		cart.setTotalQuantity(5);
		Order order = new Order();

	}
}
