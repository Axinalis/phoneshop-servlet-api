package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.enums.PaymentMethodType;
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
import java.util.ArrayList;
import java.util.List;

import static com.es.phoneshop.constant.ConstantStrings.*;
import static org.mockito.Mockito.*;

public class FinalCheckoutPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    private Order order;

    private FinalCheckoutPageServlet page = new FinalCheckoutPageServlet();

    @Before
    public void setup(){
        order = new Order();
        order.setPaymentType(PaymentMethodType.BITCOINS);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/finalCheckoutOrder.jsp")).thenReturn(dispatcher);

    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        page.doGet(request, response);
        verify(response, times(1)).sendError(404, ORDER_NOT_AVAILABLE);

        when(session.getAttribute(CURRENT_ORDER)).thenReturn(order);
        page.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/finalCheckoutOrder.jsp");
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        List<Order> orders = new ArrayList<>();

        when(session.getAttribute(CURRENT_ORDER)).thenReturn(order);
        when(session.getAttribute(STRING_SESSION_ATTRIBUTE_ORDER)).thenReturn(orders);

        page.doPost(request, response);
        verify(response, times(1)).sendRedirect(PROJECT_NAME + "/products/order/overview/" + order.getSecureId() + "?orderPlaced=true");

        Order order1 = new Order();
        order1.setPaymentType(PaymentMethodType.CREDIT_CARD);
        order1.setId(30L);
        when(session.getAttribute(CURRENT_ORDER)).thenReturn(order1);
        page.doPost(request, response);
        verify(response, times(1)).sendError(404);
    }

}
