package com.es.phoneshop.model.cart.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductPageState;
import com.es.phoneshop.exception.WrongQuantityValueOnProductPageException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;


public class DefaultCartService implements CartService{

	private static volatile DefaultCartService instance;
	
	private ProductDao productDao;
	
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
	public synchronized Cart getCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(session.getAttribute(ConstantStrings.stringSessionAttributeCart) == null) {
			session.setAttribute(ConstantStrings.stringSessionAttributeCart, new Cart());
		}
		
		return (Cart)session.getAttribute(ConstantStrings.stringSessionAttributeCart);
	}

	@Override
	public synchronized void add(Long productId, int quantity, HttpServletRequest request) {
		if(quantity <= 0) {
			throw new WrongQuantityValueOnProductPageException(ProductPageState.NEGATIVE_VALUE);
		}

		Product product = productDao.getProduct(productId);
		
		if(product.getStock() < quantity) {
			throw new WrongQuantityValueOnProductPageException(ProductPageState.OUT_OF_STOCK);
		}
		
		Optional<CartItem> sameProduct = getCart(request)
		.getItems()
		.stream()
		.filter(item -> product.equals(item.getProduct()))
		.findFirst();

		if(sameProduct.isPresent()) {
			int bufQuantity = sameProduct.get().getQuantity();
			if((bufQuantity + quantity) > product.getStock()){
				throw new WrongQuantityValueOnProductPageException(ProductPageState.OUT_OF_STOCK);
			}
			sameProduct.get().setQuantity(bufQuantity + quantity);
		} else {
			getCart(request).getItems().add(new CartItem(product, quantity));
		}
	}

	@Override
	public void remove(Long productId, int quantity, HttpServletRequest request) {
		add(productId, -quantity, request);
	}
	
}
