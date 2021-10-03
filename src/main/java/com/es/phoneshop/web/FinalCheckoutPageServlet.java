package com.es.phoneshop.web;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.constant.ConstantStrings.ORDER;

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
        if(request.getSession().getAttribute(ORDER) == null){
            response.sendError(404);
        }
        request.getRequestDispatcher("/WEB-INF/pages/finalCheckoutOrder.jsp").forward(request, response);
    }
}
