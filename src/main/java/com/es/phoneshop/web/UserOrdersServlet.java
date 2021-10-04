package com.es.phoneshop.web;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.validator.ErrorResolver;
import com.es.phoneshop.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/UserOrdersServlet")
public class UserOrdersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public UserOrdersServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Order> orders = (ArrayList)request.getSession().getAttribute(STRING_SESSION_ATTRIBUTE_ORDER);
    	request.setAttribute(ORDERS_LIST, orders);

		request.getRequestDispatcher("/WEB-INF/pages/ordersList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
	}


}
