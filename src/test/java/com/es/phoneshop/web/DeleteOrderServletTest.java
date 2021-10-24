package com.es.phoneshop.web;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.es.phoneshop.constant.ConstantStrings.PROJECT_NAME;
import static com.es.phoneshop.constant.ConstantStrings.STRING_SESSION_ATTRIBUTE_ORDER;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class DeleteOrderServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    private Order order;
    private String uuid;

    private DeleteOrderServlet servlet = new DeleteOrderServlet();

    @Before
    public void setup() throws ServletException {
        List<Order> orders = new ArrayList<>();
        servlet.init();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn("");
        order = new Order();
        uuid = UUID.randomUUID().toString();
        order.setSecureId(uuid);
        orders.add(order);
        when(session.getAttribute(STRING_SESSION_ATTRIBUTE_ORDER)).thenReturn(orders);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(response, times(1)).sendError(400);

        when(request.getPathInfo()).thenReturn("/" + uuid);
        servlet.doPost(request, response);
        verify(response, times(1)).sendRedirect(PROJECT_NAME + "/products/order/overview");

        try{
            servlet.doPost(request, response);
            fail();
        } catch (OrderNotFoundException ignored){}
    }

}
