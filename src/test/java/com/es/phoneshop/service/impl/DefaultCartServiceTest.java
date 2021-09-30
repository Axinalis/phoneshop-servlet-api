package com.es.phoneshop.service.impl;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultCartServiceTest {

	@Mock
	private HttpServletRequest request;
	@InjectMocks
	private HttpSession session;

	private DefaultCartService service;
	private Cart cart;
	private ProductDao dao = ArrayListProductDao.getInstance();
	
	@Before
	public void setup() { 
		service = DefaultCartService.getInstance();
		Currency usd = Currency.getInstance("USD");
		dao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
		dao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
		dao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
		dao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
		cart = new Cart();
		request = mock(HttpServletRequest.class);
		session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute(ConstantStrings.STRING_SESSION_ATTRIBUTE_CART)).thenReturn(cart);
	}
	
	@Test
	public void testAdding() {
		//Didn't find assertThrow in junit, therefore here we are
		service.add(1L, 1, service.getCart(request));
		try{
			service.add(0L, 1, service.getCart(request));
			fail();
		} catch (ProductNotFoundException ex){}
		try{
			service.add(9L, 1, service.getCart(request));
			fail();
		} catch (ProductNotFoundException ex){}

		try{
			service.add(1L, 10000, service.getCart(request));
			fail();
		} catch (ValidationException ex){}
		try{
			service.add(1L, -1, service.getCart(request));
			fail();
		} catch (ValidationException ex){}
		try{
			service.add(1L, 0, service.getCart(request));
			fail();
		} catch (ValidationException ex){}
		try{
			service.add(1L, -10000, service.getCart(request));
			fail();
		} catch (ValidationException ex){}

		try{
			service.add(1L, 1, null);
			fail();
		} catch (NullPointerException ex){}
		
	}

	@Test
	public void testDeleting(){
		service.add(1L, 20, cart);
		service.add(1L, 20, cart);
		service.add(3L, 3, cart);
		service.add(4L, 2, cart);
		try{
			service.add(2L, 20, cart);
		} catch(ValidationException ignored) {

		}

		service.delete(1L, cart);
		assertEquals(0, cart.getItems().stream().filter(item -> item.getProduct().getId().equals(1L)).count());
		service.delete(1L, cart);
		assertEquals(0, cart.getItems().stream().filter(item -> item.getProduct().getId().equals(1L)).count());

	}

	@Test
	public void testUpdating(){
		service.add(1L, 20, cart);
		service.add(1L, 20, cart);
		service.add(3L, 3, cart);
		service.add(4L, 2, cart);
		try{
			service.add(2L, 20, cart);
		} catch(ValidationException ignored) {

		}

		assertEquals(cart.getTotalCost(), new BigDecimal(5300));
		service.update(1L, 50, cart);
		assertEquals(50, cart.getItems().stream().filter(item -> item.getProduct().getId().equals(1L)).findFirst().get().getQuantity());
		assertEquals(cart.getTotalCost(), new BigDecimal(6300));
		service.update(3L, 0, cart);
		assertEquals(0, cart.getItems().stream().filter(item -> item.getProduct().getId().equals(3L)).count());
		assertEquals(cart.getTotalCost(), new BigDecimal(5400));
		try{
			service.update(1L, -5, cart);
			fail();
		} catch(ValidationException ignored){

		}
		assertEquals(50, cart.getItems().stream().filter(item -> item.getProduct().getId().equals(1L)).findFirst().get().getQuantity());
		assertEquals(cart.getTotalCost(), new BigDecimal(5400));

		try{
			service.update(1L, 555, cart);
			fail();
		} catch(ValidationException ignored){

		}
		assertEquals(50, cart.getItems().stream().filter(item -> item.getProduct().getId().equals(1L)).findFirst().get().getQuantity());
		assertEquals(cart.getTotalCost(), new BigDecimal(5400));

	}

}
