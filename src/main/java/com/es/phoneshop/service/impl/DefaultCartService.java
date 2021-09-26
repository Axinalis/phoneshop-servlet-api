package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.CartAddingState;
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
import java.util.stream.Stream;

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
			throw new ValidationException(CartAddingState.NEGATIVE_VALUE.toString().toLowerCase());
		}

		Product product = productDao.getProduct(productId);
		
		if(product.getStock() < quantity) {
			throw new ValidationException(CartAddingState.OUT_OF_STOCK.toString().toLowerCase());
		}

		Optional<CartItem> existingItem = getOptionalCartItem(getCart(request), product);

		lock.writeLock().lock();
		if(existingItem.isPresent()) {
			int bufQuantity = existingItem.get().getQuantity();
			if((bufQuantity + quantity) > product.getStock()){
				throw new ValidationException(CartAddingState.OUT_OF_STOCK.toString().toLowerCase());
			}
			existingItem.get().setQuantity(bufQuantity + quantity);
		} else {
			getCart(request).getItems().add(new CartItem(product, quantity));
		}
		lock.writeLock().unlock();

	}

	@Override
	public void delete(Long productId, HttpServletRequest request) {
		getCart(request)
				.getItems()
				.removeIf(item -> productId.equals(item.getProduct().getId()));
	}

	@Override
	public void update(Long productId, int quantity, HttpServletRequest request) {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		if(quantity < 0) {
			throw new ValidationException(CartAddingState.NEGATIVE_VALUE.toString().toLowerCase());
		}

		Product product = productDao.getProduct(productId);

		if(product.getStock() < quantity) {
			throw new ValidationException(CartAddingState.OUT_OF_STOCK.toString().toLowerCase());
		}

		Optional<CartItem> existingItem = getOptionalCartItem(getCart(request), product);

		lock.writeLock().lock();
		//Deleting by setting quantity to zero
		if(quantity == 0){
			getCart(request)
					.getItems()
					.removeIf(item -> productId.equals(item.getProduct().getId()));
		} else {
			if(existingItem.isPresent()) {
				existingItem.get().setQuantity(quantity);
			} else {
				getCart(request).getItems().add(new CartItem(product, quantity));
			}
		}
		lock.writeLock().unlock();
	}

	//Method will return Optional<CartItem> with all items in cart (from none to one)
	//If there is several items, method will also fix it
	private Optional<CartItem> getOptionalCartItem(Cart cart, Product product){
		ReadWriteLock lock = new ReentrantReadWriteLock();

		lock.readLock().lock();
		Stream<CartItem> existingItems = getStreamCartItem(cart, product);
		lock.readLock().unlock();

		if(existingItems.count() > 1){
			//Getting sum of all same-id-product quantities and setting that sum to the first fitting item in cart
			CartItem firstItem = new CartItem(product, 0);
			int count = existingItems.reduce(
					firstItem,
					(combinedItem, item) -> {
						combinedItem.setQuantity(combinedItem.getQuantity() + item.getQuantity());
						return combinedItem;
					})
					.getQuantity();
			lock.writeLock().lock();
			{
				existingItems.findFirst().get().setQuantity(count);
				cart.getItems().removeIf(item -> (
						product.getId().equals(item.getProduct().getId()))
						&& item.getQuantity() != count);
			}
			lock.writeLock().unlock();
		} else{
			existingItems.close();
		}

		return getStreamCartItem(cart, product).findFirst();
	}

	//Method for starting a stream
	private Stream<CartItem> getStreamCartItem(Cart cart, Product product){
		Stream<CartItem> existingItem = cart
				.getItems()
				.stream()
				.filter(item -> product.getId().equals(item.getProduct().getId()));

		return existingItem;
	}

}
