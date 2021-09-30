package com.es.phoneshop.web;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static com.es.phoneshop.constant.ConstantStrings.MINI_CART_FULL;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {

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

    private DefaultCartService service;
    private Cart cart;
    private ProductDao dao = ArrayListProductDao.getInstance();
    private MiniCartServlet page;
    private CartService cartService = DefaultCartService.getInstance();
    
    @Before
    public void setup() throws ServletException{
    	page = new MiniCartServlet();
        Currency usd = Currency.getInstance("USD");
        dao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
        dao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        dao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        dao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
        dao.save(new Product("iphone3", "Apple iPhone3", new BigDecimal(700), usd, 10, "/Apple/Apple%20iPhone.jpg"));
    	request = mock(HttpServletRequest.class);
    	session = mock(HttpSession.class);
    	disp = mock(RequestDispatcher.class);
    	cart = new Cart();
    	service = DefaultCartService.getInstance();
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getParameter(ConstantStrings.QUANTITY)).thenReturn("5");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/minicart.jsp")).thenReturn(disp);
        when(session.getAttribute(ConstantStrings.RECENTLY_VIEWED)).thenReturn(new UserViewsHistory());
        when(session.getAttribute(ConstantStrings.STRING_SESSION_ATTRIBUTE_CART)).thenReturn(new Cart());
        when(request.getParameter(ConstantStrings.UPDATING)).thenReturn("true");
        page.init(config);
    }

	@Test
	public void testDoPost(){

        try{
            page.doGet(request, response);
        } catch(ServletException | IOException ex){
            fail();
        }

        service.add(1L, 20, cart);
        service.add(1L, 20, cart);
        service.add(3L, 3, cart);
        service.add(4L, 2, cart);
        service.add(5L, 2, cart);

        assertTrue(cart.getItems().size() == 4);

        try{
            page.doGet(request, response);
        } catch(ServletException | IOException ex){
            fail();
        }
    }


}
