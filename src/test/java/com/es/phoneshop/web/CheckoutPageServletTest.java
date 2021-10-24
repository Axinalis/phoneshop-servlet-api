package com.es.phoneshop.web;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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

import static com.es.phoneshop.constant.ConstantStrings.PROJECT_NAME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckoutPageServletTest {

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
    private CheckoutPageServlet page;
    private CartService cartService = DefaultCartService.getInstance();

    @Before
    public void setup() throws ServletException {
        page = new CheckoutPageServlet();
        Cart cart  = new Cart();
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        response = mock(HttpServletResponse.class);
        disp = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/checkoutOrder.jsp")).thenReturn(disp);
        when(session.getAttribute(ConstantStrings.STRING_SESSION_ATTRIBUTE_CART)).thenReturn(cart);
        page.init(config);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        page.doGet(request, response);
    }

}
