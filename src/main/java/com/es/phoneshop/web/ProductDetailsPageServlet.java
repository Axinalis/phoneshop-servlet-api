package com.es.phoneshop.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.service.ProductDao;
import com.es.phoneshop.service.impl.ArrayListProductDao;
import com.es.phoneshop.constant.ProductPageState;
import com.es.phoneshop.exception.WrongQuantityValueOnProductPageException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.validator.PageStateResolver;
import com.es.phoneshop.validator.Validator;

@WebServlet("/ProductDetailsPageServlet")
public class ProductDetailsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDao productDao;
	private CartService cartService;

	public ProductDetailsPageServlet() {
		super();
		// TODO Auto-generated constructor stub
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
		String productId = request.getPathInfo();
		productDao = ArrayListProductDao.getInstance();
		Product product;
		UserViewsHistory history;
		
		id = Validator.validatingId(productId);
		product = productDao.getProduct(id);
		history = (UserViewsHistory)request.getSession().getAttribute(ConstantStrings.RECENTLY_VIEWED);
		
		if(history != null) {
			history.addProduct(product);
		} else {
			request.getSession().setAttribute(ConstantStrings.RECENTLY_VIEWED, new UserViewsHistory());
		}

		String successParameter = request.getParameter(ConstantStrings.SUCCESS);
		String errorParameter = request.getParameter(ConstantStrings.ERROR);
		if(successParameter != null && !successParameter.equals("")){
			request.setAttribute(ConstantStrings.SUCCESS, PageStateResolver.getMessageFromState(successParameter));
		}
		if(errorParameter != null && !errorParameter.equals("")){
			request.setAttribute(ConstantStrings.ERROR,	PageStateResolver.getMessageFromState(errorParameter));
		}

		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id;
		int quantityInt = 0;
		String stateInfo;
		String productId = request.getPathInfo();
		id = Validator.validatingId(productId);
		
		try {
			quantityInt = Validator.parsingQuantity(request.getParameter(ConstantStrings.QUANTITY), request.getLocale());
			cartService.add(id, quantityInt, request);
			stateInfo = ConstantStrings.SUCCESS + "=" + ProductPageState.PRODUCT_ADDED.toString().toLowerCase();
		} catch(WrongQuantityValueOnProductPageException ex) {
			stateInfo = ConstantStrings.ERROR + "=" + ex.getState().toString().toLowerCase();
		}

		response.sendRedirect("/phoneshop-servlet-api/products/info/" + id.toString() + "?" + stateInfo);
	}

}
