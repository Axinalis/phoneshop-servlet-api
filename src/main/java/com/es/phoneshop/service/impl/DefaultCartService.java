package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.ProductPageState;
import com.es.phoneshop.exception.ValidationException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.es.phoneshop.constant.ConstantStrings.STRING_SESSION_ATTRIBUTE_CART;

public class DefaultCartService implements CartService {

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
	public Cart getCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(session.getAttribute(STRING_SESSION_ATTRIBUTE_CART) == null) {
			synchronized(session){
				if(session.getAttribute(STRING_SESSION_ATTRIBUTE_CART) == null){
					session.setAttribute(STRING_SESSION_ATTRIBUTE_CART, new Cart());
				}
			}

		}
		
		return (Cart)session.getAttribute(STRING_SESSION_ATTRIBUTE_CART);
	}

	@Override
	public void add(Long productId, int quantity, HttpServletRequest request) {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		if(quantity <= 0) {
			throw new ValidationException(ProductPageState.NEGATIVE_VALUE.toString().toLowerCase());
		}

		Product product = productDao.getProduct(productId);
		
		if(product.getStock() < quantity) {
			throw new ValidationException(ProductPageState.OUT_OF_STOCK.toString().toLowerCase());
		}

		lock.readLock().lock();
		Cart cart = getCart(request);
		Optional<CartItem> existingItem = cart
				.getItems()
				.stream()
				.filter(item -> product.equals(item.getProduct()))
				.findFirst();
		lock.readLock().unlock();

		lock.writeLock().lock();
		if(existingItem.isPresent()) {
			int bufQuantity = existingItem.get().getQuantity();
			if((bufQuantity + quantity) > product.getStock()){
				throw new ValidationException(ProductPageState.OUT_OF_STOCK.toString().toLowerCase());
			}
			existingItem.get().setQuantity(bufQuantity + quantity);
		} else {
			getCart(request).getItems().add(new CartItem(product, quantity));
		}
		lock.writeLock().unlock();

	}

	@Override
	public void remove(Long productId, int quantity, HttpServletRequest request) {
		throw new RuntimeException("Not implemented");
		//add(productId, -quantity, request);
	}
	
}
