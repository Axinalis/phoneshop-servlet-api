package com.es.phoneshop.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductPageState;
import com.es.phoneshop.exception.WrongQuantityValueOnProductPageException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.impl.DefaultCartService;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.validator.PageStateResolver;
import com.es.phoneshop.validator.Validator;

/**
 * Servlet implementation class ProductDetailsPageServlet
 */
@WebServlet("/ProductDetailsPageServlet")
public class ProductDetailsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDao productDao;
	private CartService cartService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id;
		String productId = request.getPathInfo();
		productDao = ArrayListProductDao.getInstance();
		Product product;
		UserViewsHistory history;
		
		id = Validator.validatingId(productId);
		product = productDao.getProduct(id);
		history = (UserViewsHistory)request.getSession().getAttribute(ConstantStrings.recentlyViewed);
		
		if(history != null) {
			history.addProduct(product);
		} else {
			request.getSession().setAttribute(ConstantStrings.recentlyViewed, new UserViewsHistory());
		}
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id;
		int quantityInt = 0;
		
		String productId = request.getPathInfo();
		id = Validator.validatingId(productId);
		
		try {
			quantityInt = Validator.validatingQuantity(request.getParameter(ConstantStrings.quantity));
			cartService.add(id, quantityInt, request);
			request.setAttribute(ConstantStrings.success, PageStateResolver.getMessageFromState(ProductPageState.PRODUCT_ADDED));
		} catch(WrongQuantityValueOnProductPageException ex) {
			request.setAttribute(ConstantStrings.error, PageStateResolver.getMessageFromState(ex.getState()));
		}
		
		request.setAttribute("product", productDao.getProduct(id));
		request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp").forward(request, response);
	}

}
