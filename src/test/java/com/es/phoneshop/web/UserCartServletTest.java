package com.es.phoneshop.web;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static com.es.phoneshop.constant.ConstantStrings.PRODUCT_ID;
import static com.es.phoneshop.constant.ConstantStrings.QUANTITY;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserCartServletTest {

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
    private UserCartServlet page;
    private CartService cartService = DefaultCartService.getInstance();
    
    @Before
    public void setup() throws ServletException{
    	page = new UserCartServlet();
    	Cart cart  = new Cart();
        Currency usd = Currency.getInstance("USD");
        dao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
        dao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        dao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        dao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
        DefaultCartService.getInstance().add((long)3, 5, cart);
    	request = mock(HttpServletRequest.class);
    	session = mock(HttpSession.class);
    	disp = mock(RequestDispatcher.class);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/cartProductList.jsp")).thenReturn(disp);
        when(session.getAttribute(ConstantStrings.STRING_SESSION_ATTRIBUTE_CART)).thenReturn(cart);
        when(request.getParameter(ConstantStrings.UPDATING)).thenReturn("true");
        page.init(config);
    }
    
	@Test
	public void testDoGet() {
		try {
			page.doGet(request, response);
		} catch (ServletException | IOException e) {
			fail();
		} catch (NullPointerException ex) {
		    fail();
        }

		when(request.getParameterValues(PRODUCT_ID)).thenReturn("1 3 1".split(" "));
		when(request.getParameterValues(QUANTITY)).thenReturn("5 4 5".split(" "));
		try{
            page.doGet(request, response);
        } catch (ServletException | IOException e){
		    fail();
        }

        when(request.getParameterValues(PRODUCT_ID)).thenReturn("1 3 1".split(" "));
        when(request.getParameterValues(QUANTITY)).thenReturn(null);
        try{
            page.doGet(request, response);
            fail();
        } catch (ServletException | IOException e){
            fail();
        } catch (NullPointerException ignored){

        }

        when(request.getParameterValues(PRODUCT_ID)).thenReturn(null);
        when(request.getParameterValues(QUANTITY)).thenReturn("5 4 5".split(" "));
        try{
            page.doGet(request, response);
        } catch (ServletException | IOException e){
            fail();
        }
	}

}
