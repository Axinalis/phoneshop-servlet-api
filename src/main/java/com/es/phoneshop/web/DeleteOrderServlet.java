package com.es.phoneshop.web;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;
import com.es.phoneshop.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.constant.ConstantStrings.PROJECT_NAME;

@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private OrderService orderService;

    public DeleteOrderServlet() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		orderService = DefaultOrderService.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String rawSecureId = request.getPathInfo();
    	if(rawSecureId == null || rawSecureId.length() < 2){
    		response.sendError(400);
    		return;
		}
    	String secureId = rawSecureId.substring(1);
		orderService.delete(secureId, request);
		response.sendRedirect(PROJECT_NAME + "/products/order/overview");

	}
}
