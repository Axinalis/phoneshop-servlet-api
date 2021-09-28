package com.es.phoneshop.web;

import com.es.phoneshop.comparator.filter.FilterCreator;
import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
	
	private ProductDao productDao;
	private CartService cartService;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		productDao = ArrayListProductDao.getInstance();
		cartService = DefaultCartService.getInstance();
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	if(request.getSession().getAttribute(ConstantStrings.RECENTLY_VIEWED) == null) {
    		request.getSession().setAttribute(ConstantStrings.RECENTLY_VIEWED, new UserViewsHistory());
    	}
		if(request.getSession().getAttribute(ConstantStrings.CART) == null) {
			request.getSession().setAttribute(ConstantStrings.CART, new Cart());
		}

        //Filter-creating logic
        FilterCreator creator = new FilterCreator();
        creator.setQuery(request.getParameter(ConstantStrings.QUERY));
        creator.setSorting(request.getParameter(ConstantStrings.FIELD), request.getParameter(ConstantStrings.ORDER));
        
    	request.setAttribute("products", productDao.findProducts(creator.createFilter()));
    	request.setAttribute(ConstantStrings.CART, cartService.getCart(request) == null ? new Cart() : cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
