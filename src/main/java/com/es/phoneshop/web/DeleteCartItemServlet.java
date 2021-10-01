package com.es.phoneshop.web;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.validator.ErrorResolver;
import com.es.phoneshop.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/DeleteCartItemServlet")
public class DeleteCartItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CartService cartService;

    public DeleteCartItemServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		cartService = DefaultCartService.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String productId = request.getPathInfo();
    	cartService.delete(Validator.validatingId(productId.substring(1)), cartService.getCart(request));

    	response.sendRedirect("/phoneshop-servlet-api/products/cart");
	}
}
