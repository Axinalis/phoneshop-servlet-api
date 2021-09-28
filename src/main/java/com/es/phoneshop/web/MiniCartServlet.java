package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/MiniCartServlet")
public class MiniCartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CartItem> miniCart = cartService.getCart(request).getItems();
        if(miniCart.size() > 3){
            miniCart = miniCart.subList(0, 2);
            request.setAttribute("miniCartFull", "true");
        }
        request.setAttribute("miniCart", miniCart);
        request.getRequestDispatcher("/WEB-INF/pages/minicart.jsp").include(request, response);
    }
}
