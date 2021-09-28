package com.es.phoneshop.web;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.enums.CartAddingState;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.validator.ErrorResolver;
import com.es.phoneshop.validator.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebServlet("/ProductDetailsPageServlet")
public class ProductDetailsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDao productDao;
	private CartService cartService;

	public ProductDetailsPageServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productDao = ArrayListProductDao.getInstance();
		cartService = DefaultCartService.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id;
		String productId = request.getPathInfo().substring(1);
		productDao = ArrayListProductDao.getInstance();
		Product product;
		UserViewsHistory history;
		
		id = Validator.validatingId(productId);
		product = productDao.getProduct(id);
		history = (UserViewsHistory)request.getSession().getAttribute(RECENTLY_VIEWED);
		
		if(history != null) {
			history.addProduct(product);
		} else {
			request.getSession().setAttribute(RECENTLY_VIEWED, new UserViewsHistory());
		}

		String successParameter = request.getParameter(SUCCESS);
		String errorParameter = request.getParameter(ERROR);
		if(successParameter != null && !successParameter.equals("")){
			request.setAttribute(SUCCESS, ErrorResolver.getMessageFromState(successParameter));
		}
		if(errorParameter != null && !errorParameter.equals("")){
			request.setAttribute(ERROR,	ErrorResolver.getMessageFromState(errorParameter));
		}

		request.setAttribute(ConstantStrings.CART, cartService.getCart(request));
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id;
		int quantityInt;
		String stateInfo;
		String productId = request.getPathInfo().substring(1);
		id = Validator.validatingId(productId);
		
		try {
			quantityInt = Validator.parsingQuantity(request.getParameter(QUANTITY), request.getLocale());
			cartService.add(id, quantityInt, request);
			stateInfo = SUCCESS + "=" + CartAddingState.PRODUCT_ADDED.toString().toLowerCase();
		} catch(ValidationException ex) {
			stateInfo = ERROR + "=" + ex.getMessage().toLowerCase();
		}

		response.sendRedirect("/phoneshop-servlet-api/products/info/" + id + "?" + stateInfo);
	}

}
