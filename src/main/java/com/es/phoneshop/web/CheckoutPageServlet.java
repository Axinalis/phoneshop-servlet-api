package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
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

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/CheckoutPageServlet")
public class CheckoutPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartService cartService;
    private OrderService orderService;

    public CheckoutPageServlet() {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart)request.getSession().getAttribute(STRING_SESSION_ATTRIBUTE_CART);
        if(cart == null || cart.getItems() == null || cart.getItems().size() < 1){
            response.sendRedirect(PROJECT_NAME + "/products/cart");
        } else {
            request.getSession().setAttribute(CURRENT_ORDER, orderService.getOrder(cartService.getCart(request)));
            request.getRequestDispatcher("/WEB-INF/pages/checkoutOrder.jsp").forward(request, response);
        }
    }
}
