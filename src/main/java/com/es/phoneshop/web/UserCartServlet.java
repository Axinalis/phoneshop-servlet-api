package com.es.phoneshop.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;

@WebServlet("/UserCartServlet")
public class UserCartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CartService cartService;

    public UserCartServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		cartService = DefaultCartService.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = cartService.getCart(request);
		
		request.setAttribute(ConstantStrings.CART_LIST, cart.getItems());
		request.getRequestDispatcher("/WEB-INF/pages/cartProductList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
