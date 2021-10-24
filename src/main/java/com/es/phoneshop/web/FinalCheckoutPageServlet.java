package com.es.phoneshop.web;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;
import com.es.phoneshop.validator.PaymentTypeResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/FinalCheckoutPageServlet")
public class FinalCheckoutPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartService cartService;
    private OrderService orderService;

    public FinalCheckoutPageServlet() {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute(CURRENT_ORDER) == null){
            response.sendError(404, ORDER_NOT_AVAILABLE);
        } else {
            request.setAttribute(PAYMENT_TYPE, PaymentTypeResolver
                    .GetMessageFromType(((Order)request
                            .getSession()
                            .getAttribute(CURRENT_ORDER))
                            .getPaymentType()));

            request.getRequestDispatcher("/WEB-INF/pages/finalCheckoutOrder.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = (Order) request.getSession().getAttribute(CURRENT_ORDER);
        try{
            orderService.placeOrder(order);
            List<Order> orders = (ArrayList)request.getSession().getAttribute(STRING_SESSION_ATTRIBUTE_ORDER);
            orders.add(order);
            request.getSession().setAttribute(STRING_SESSION_ATTRIBUTE_ORDER, orders);
            request.getSession().setAttribute(STRING_SESSION_ATTRIBUTE_CART, new Cart());
            request.getSession().removeAttribute(CURRENT_ORDER);
            response.sendRedirect(PROJECT_NAME + "/products/order/overview/" + order.getSecureId() + "?orderPlaced=true");
        } catch (IllegalArgumentException | OrderNotFoundException ex){
            response.sendError(404);
        }

    }
}
