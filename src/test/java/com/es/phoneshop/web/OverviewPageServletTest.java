package com.es.phoneshop.web;

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
import java.util.UUID;

import static com.es.phoneshop.constant.ConstantStrings.ORDER_PLACED;
import static com.es.phoneshop.constant.ConstantStrings.STRING_SESSION_ATTRIBUTE_ORDER;
import static org.mockito.Mockito.*;

public class OverviewPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    private Order order;
    private String uuid;

    private OverviewPageServlet page = new OverviewPageServlet();

    @Before
    public void setup() throws ServletException {
        List<Order> orders = new ArrayList<>();
        page.init();
        order = new Order();
        uuid = UUID.randomUUID().toString();
        order.setSecureId(uuid);
        order.setPaymentType(PaymentMethodType.CACHE);
        orders.add(order);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("");
        when(request.getRequestDispatcher("/WEB-INF/pages/overviewOrder.jsp")).thenReturn(dispatcher);
        when(session.getAttribute(STRING_SESSION_ATTRIBUTE_ORDER)).thenReturn(orders);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        page.doGet(request, response);
        verify(response, times(1)).sendError(400);

        when(request.getPathInfo()).thenReturn("/" + UUID.randomUUID());
        page.doGet(request, response);
        verify(response, times(1)).sendError(404);

        when(request.getPathInfo()).thenReturn("/" + uuid);
        page.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/pages/overviewOrder.jsp");
    }

}
