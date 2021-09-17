package com.es.phoneshop.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.validator.ProductIdValidator;

/**
 * Servlet implementation class ProductDetailsPageServlet
 */
@WebServlet("/ProductDetailsPageServlet")
public class ProductDetailsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductDao productDao;

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
		
		id = ProductIdValidator.validadingId(productId);
		
		request.setAttribute("product", productDao.getProduct(id));
		request.getRequestDispatcher("/WEB-INF/pages/productInfo.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
