package com.es.phoneshop.model.cart.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;


public class DefaultCartService implements CartService{

	private static volatile DefaultCartService instance;
	
	private ProductDao productDao;
	private static String stringCart = "cart";
	
	private DefaultCartService() {
		productDao = ArrayListProductDao.getInstance();
	}
	
	public static DefaultCartService getInstance() {
		DefaultCartService result = instance;
		if(result != null) {
			return result;
		}
		
		synchronized(DefaultCartService.class) {
			if(instance == null) {
				instance = new DefaultCartService();
			}
			return instance;
		}
	}
	
	@Override
	public Cart getCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(session.getAttribute(stringCart) == null) {
			session.setAttribute(stringCart, new Cart());
		}
		
		return (Cart)session.getAttribute(stringCart);
	}

	@Override
	public void add(Long productId, int quantity, HttpServletRequest request) {
		Product product = productDao.getProduct(productId);
		
		Optional<CartItem> sameProduct = getCart(request)
		.getItems()
		.stream()
		.filter(item -> product.equals(item.getProduct()))
		.findFirst();
		
		if(sameProduct.isEmpty()) {
			getCart(request).getItems().add(new CartItem(product, quantity));
		} else {
			int bufQuantity = sameProduct.get().getQuantity();
			sameProduct.get().setQuantity(bufQuantity + quantity);
		}
		
	}

	@Override
	public void remove(Long productId, int quantity, HttpServletRequest request) {
		add(productId, -quantity, request);
		
	}

	
	
}
