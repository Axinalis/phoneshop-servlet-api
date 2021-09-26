package com.es.phoneshop.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.validator.ErrorResolver;
import com.es.phoneshop.validator.Validator;

import static com.es.phoneshop.constant.ConstantStrings.*;

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
		String[] productIds = request.getParameterValues(PRODUCT_ID);
		String[] quantities = request.getParameterValues(QUANTITY);
		Locale locale = request.getLocale();
		Map<Long, String> errors = new HashMap<>();
		Long id = null;
		int quantity = 0;

    	for(int i = 0; i < productIds.length; i++){
    		try{
    			id = Validator.validatingId(productIds[i]);
    			quantity = Validator.parsingQuantity(quantities[i], locale);
				cartService.update(id, quantity, request);
			} catch(IllegalArgumentException ex){
    			throw ex;
			} catch(ValidationException ex){
    			errors.put(id, ErrorResolver.getMessageFromState(ex.getMessage()) + " (not " + quantities[i] + ")");
			}
		}

    	request.setAttribute("errors", errors);
		Cart cart = cartService.getCart(request);

		request.setAttribute(ConstantStrings.CART_LIST, cart.getItems());
		request.getRequestDispatcher("/WEB-INF/pages/cartProductList.jsp").forward(request, response);;
	}

}
