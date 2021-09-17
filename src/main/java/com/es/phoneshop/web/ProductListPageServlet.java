package com.es.phoneshop.web;

import com.es.phoneshop.comparator.filter.FilterCreator;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
	
	private ProductDao productDao;
	private String field = "field";
	private String order = "order";
    private String query = "query";
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		
		super.init(config);
		productDao = ArrayListProductDao.getInstance();
		
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        //Filter-creating logic
        FilterCreator creator = new FilterCreator();
        creator.setQuery(request.getParameter(query));
        creator.setSorting(request.getParameter(field), request.getParameter(order));
        
    	request.setAttribute("products", productDao.findProducts(creator.createFilter()));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
