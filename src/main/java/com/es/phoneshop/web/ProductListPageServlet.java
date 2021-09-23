package com.es.phoneshop.web;

import com.es.phoneshop.browsing.filter.FilterCreator;
import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.service.ProductDao;
import com.es.phoneshop.service.impl.ArrayListProductDao;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
	
	private ProductDao productDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		productDao = ArrayListProductDao.getInstance();
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getSession().getAttribute(ConstantStrings.RECENTLY_VIEWED) == null) {
    		request.getSession().setAttribute(ConstantStrings.RECENTLY_VIEWED, new UserViewsHistory());
    	}
    	
        //Filter-creating logic
        FilterCreator creator = new FilterCreator();
        creator.setQuery(request.getParameter(ConstantStrings.QUERY));
        creator.setSorting(request.getParameter(ConstantStrings.FIELD), request.getParameter(ConstantStrings.ORDER));
        
    	request.setAttribute("products", productDao.findProducts(creator.createFilter()));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
