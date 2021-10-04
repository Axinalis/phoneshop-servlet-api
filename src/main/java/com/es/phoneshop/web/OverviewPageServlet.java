package com.es.phoneshop.web;

import com.es.phoneshop.exception.OrderNotFoundException;
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

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/OverviewPageServlet")
public class OverviewPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartService cartService;
    private OrderService orderService;

    public OverviewPageServlet() {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureID = request.getPathInfo().substring(1);
        Order order = null;
        try{
            order = orderService.getOrderByUUID(secureID, request);
        } catch (OrderNotFoundException ex){
            response.sendError(404);
        }

        if("true".equals(request.getParameter(ORDER_PLACED))){
            request.setAttribute(ORDER_PLACED, "true");
        }
        request.setAttribute(PAYMENT_TYPE, PaymentTypeResolver
                .GetMessageFromType(order
                        .getPaymentType()));
        request.setAttribute(CURRENT_ORDER, order);
        request.getRequestDispatcher("/WEB-INF/pages/overviewOrder.jsp").forward(request, response);
    }
}
