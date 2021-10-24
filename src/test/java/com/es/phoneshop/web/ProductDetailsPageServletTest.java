package com.es.phoneshop.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

	@Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher disp;

    private ProductDao dao = ArrayListProductDao.getInstance();
    private ProductDetailsPageServlet page;
    private CartService cartService = DefaultCartService.getInstance();
    
    @Before
    public void setup() throws ServletException{
    	page = new ProductDetailsPageServlet();
        Currency usd = Currency.getInstance("USD");
        dao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
        dao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        dao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        dao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
    	request = mock(HttpServletRequest.class);
    	session = mock(HttpSession.class);
    	disp = mock(RequestDispatcher.class);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getParameter(ConstantStrings.QUANTITY)).thenReturn("5");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp")).thenReturn(disp);
        when(session.getAttribute(ConstantStrings.RECENTLY_VIEWED)).thenReturn(new UserViewsHistory());
        when(session.getAttribute(ConstantStrings.STRING_SESSION_ATTRIBUTE_CART)).thenReturn(new Cart());
        page.init(config);
    }
    
	@Test
	public void testDoGet() {
		try {
			page.doGet(request, response);
		} catch (ServletException | IOException e) {
			fail();
		} catch (ProductNotFoundException ex) {
            fail();
		}
	}

	@Test
	public void testDoPost() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1");
        page.doPost(request, response);
        verify(response, times(1)).sendRedirect("/phoneshop-servlet-api/products/info/" + 1 + "?" + "success=product_added");

        when(request.getPathInfo()).thenReturn("/l");

        page.doPost(request, response);
        verify(response, times(1)).sendError(404);

    }


}
