package com.es.phoneshop.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.service.ProductDao;
import com.es.phoneshop.service.impl.ArrayListProductDao;
import com.es.phoneshop.validator.Validator;

@WebServlet("/ProductPriceHistoryServlet")
public class ProductPriceHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductDao productDao;

	public ProductPriceHistoryServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productDao = ArrayListProductDao.getInstance();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long id;
		String productId = request.getPathInfo();
		productDao = ArrayListProductDao.getInstance();
		
		id = Validator.validatingId(productId);
		
		request.setAttribute("product", productDao.getProduct(id));
		request.getRequestDispatcher("/WEB-INF/pages/productHistory.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
