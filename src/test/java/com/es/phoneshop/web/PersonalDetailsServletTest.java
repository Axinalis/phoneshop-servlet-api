package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

import static com.es.phoneshop.constant.ConstantStrings.*;
import static org.mockito.Mockito.*;

public class PersonalDetailsServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    private Cart cart;

    private PersonalDetailsServlet servlet = new PersonalDetailsServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init();
        cart = new Cart();
        cart.setTotalCost(new BigDecimal(50));
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(STRING_SESSION_ATTRIBUTE_CART)).thenReturn(cart);
        when(request.getRequestDispatcher("/WEB-INF/pages/personalDataForOrder.jsp")).thenReturn(dispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request, times(3)).getSession(); // 2 in servlet and 1 in service
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/personalDataForOrder.jsp");

        when(session.getAttribute(CURRENT_ORDER)).thenReturn(new Order());
        servlet.doGet(request, response);
        verify(request, times(4)).getSession();
        verify(request, times(2)).getRequestDispatcher("/WEB-INF/pages/personalDataForOrder.jsp");
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Order order  = new Order();

        when(session.getAttribute(CURRENT_ORDER)).thenReturn(order);
        servlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(PROJECT_NAME + "/products/order/finalCheckout");

        when(request.getParameter(FIRST_NAME)).thenReturn(null);
        when(request.getParameter(LAST_NAME)).thenReturn(null);
        when(request.getParameter(PHONE_NUMBER)).thenReturn(null);
        when(request.getParameter(DELIVERY_DATE)).thenReturn(null);
        when(request.getParameter(ADDRESS)).thenReturn(null);
        when(request.getParameter(PAYMENT_TYPE)).thenReturn(null);

        servlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(PROJECT_NAME + "/products/order/finalCheckout");

        when(request.getParameter(FIRST_NAME)).thenReturn("Anton");
        when(request.getParameter(LAST_NAME)).thenReturn("");
        when(request.getParameter(PHONE_NUMBER)).thenReturn(null);
        when(request.getParameter(DELIVERY_DATE)).thenReturn("2022-08-08");
        when(request.getParameter(ADDRESS)).thenReturn("Street");
        when(request.getParameter(PAYMENT_TYPE)).thenReturn("Bitcoins");

        servlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(PROJECT_NAME + "/products/order/finalCheckout");

        when(request.getParameter(FIRST_NAME)).thenReturn("Anton");
        when(request.getParameter(LAST_NAME)).thenReturn("Trus");
        when(request.getParameter(PHONE_NUMBER)).thenReturn("+375-29 773 39 95");
        when(request.getParameter(DELIVERY_DATE)).thenReturn("2020-08-08");
        when(request.getParameter(ADDRESS)).thenReturn("Apples str.");
        when(request.getParameter(PAYMENT_TYPE)).thenReturn(BY_BITCOINS);

        servlet.doPost(request, response);
        verify(response, times(0)).sendRedirect(PROJECT_NAME + "/products/order/finalCheckout");

        when(request.getParameter(FIRST_NAME)).thenReturn("Anton");
        when(request.getParameter(LAST_NAME)).thenReturn("Trus");
        when(request.getParameter(PHONE_NUMBER)).thenReturn("+375-29 773 39 95");
        when(request.getParameter(DELIVERY_DATE)).thenReturn("2022-08-08");
        when(request.getParameter(ADDRESS)).thenReturn("Apples str.");
        when(request.getParameter(PAYMENT_TYPE)).thenReturn(BY_BITCOINS);

        servlet.doPost(request, response);
        verify(response, times(1)).sendRedirect(PROJECT_NAME + "/products/order/finalCheckout");
    }

}
