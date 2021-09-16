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

/**
 * Servlet implementation class ProductDetailsPageServlet
 */
@WebServlet("/ProductPriceHistoryServlet")
public class ProductPriceHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductDao productDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductPriceHistoryServlet() {
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
		Long id = null;
		String productId = request.getPathInfo();
		productDao = ArrayListProductDao.getInstance();
		
		if(productId == null) {
			throw new ProductNotFoundException("Id is not valid");
		}
		try {
			id = Long.valueOf(productId.substring(1));
		} catch(NumberFormatException ex) {
			throw new ProductNotFoundException("Id is not valid");
		}
		
		request.setAttribute("product", productDao.getProduct(id));
		request.getRequestDispatcher("/WEB-INF/pages/productHistory.jsp").forward(request, response);

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
