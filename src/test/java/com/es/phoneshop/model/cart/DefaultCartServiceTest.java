package com.es.phoneshop.model.cart;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.exception.WrongQuantityValueOnProductPageException;
import com.es.phoneshop.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.es.phoneshop.model.cart.impl.DefaultCartService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.fail;
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
		when(session.getAttribute(ConstantStrings.stringSessionAttributeCart)).thenReturn(cart);
	}
	
	@Test
	public void test() {
		//Didn't find assertThrow in junit, therefore here we are
		service.add(1L, 1, request);
		try{
			service.add(0L, 1, request);
			fail();
		} catch (ProductNotFoundException ex){}
		try{
			service.add(9L, 1, request);
			fail();
		} catch (ProductNotFoundException ex){}

		try{
			service.add(1L, 10000, request);
			fail();
		} catch (WrongQuantityValueOnProductPageException ex){}
		try{
			service.add(1L, -1, request);
			fail();
		} catch (WrongQuantityValueOnProductPageException ex){}
		try{
			service.add(1L, 0, request);
			fail();
		} catch (WrongQuantityValueOnProductPageException ex){}
		try{
			service.add(1L, -10000, request);
			fail();
		} catch (WrongQuantityValueOnProductPageException ex){}

		try{
			service.add(1L, 1, null);
			fail();
		} catch (NullPointerException ex){}
		
	}
}
