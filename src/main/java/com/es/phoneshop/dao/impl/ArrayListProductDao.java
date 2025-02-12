package com.es.phoneshop.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.es.phoneshop.comparator.SortingComparator;
import com.es.phoneshop.comparator.filter.Filter;
import com.es.phoneshop.comparator.filter.FilterMatcher;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

public class ArrayListProductDao implements ProductDao {

	private static volatile ArrayListProductDao instance;
	private List<Product> products;
	public final ReadWriteLock readWriteLock;
	private static Long maxId;

	private ArrayListProductDao() {
		products = new ArrayList<Product>();
		this.readWriteLock = new ReentrantReadWriteLock();
		if(maxId == null) {
			maxId = 1L;
		}
	}
	
	public static ArrayListProductDao getInstance() {
		ArrayListProductDao result = instance;
		if(result != null) {
			return result;
		}
		
		synchronized(ArrayListProductDao.class) {
			if(instance == null) {
				instance = new ArrayListProductDao();
			}
			return instance;
		}
		
	}

    @Override
    public Product getProduct(Long id) {
    	Product buf;
		if (id == null) {
			throw new IllegalArgumentException("Id is null");
		}

		try {
			readWriteLock.readLock().lock();
			buf = products.stream()
					.filter(p -> id.equals(p.getId()))
					.findFirst()
					.orElseThrow(() -> new ProductNotFoundException("No products with current id were found (id = " + id + ")"));
		} finally {
			readWriteLock.readLock().unlock();
		}
		
		return buf;
    }

    @Override
    public List<Product> findProducts(Filter filter) {
    	List<Product> prods;
    	
    	try {
    		readWriteLock.readLock().lock();
        	prods = products.stream()
        			.filter(p -> {
        				if(filter.getQueryWords().size() == 0) {
        					return true;
        				} else {
        					return FilterMatcher.percentOfWords(p, filter) > 0;
        				}})
        			.sorted((p1, p2) -> {
        			return SortingComparator.sortProducts(p1, p2, filter);
        			})
        			.collect(Collectors.toList());
    	} finally {
    		readWriteLock.readLock().unlock();
    	}
		return prods;
    }

    @Override
    public Long save(Product product) {
    	if (product == null) {
    		throw new IllegalArgumentException("Product is null");
    	}
    	Product sameIdProduct;

    	try {
    		readWriteLock.writeLock().lock();
        	if (product.getId() == null) {
        		products.add(new Product(maxId++, product));
        	} else {
        		sameIdProduct = products.stream()
            			.filter(p -> product.getId().equals(p.getId()))
						.findFirst()
						.orElseThrow(() -> new ProductNotFoundException("No products with current id were found"));
        		products.set(products.indexOf(sameIdProduct), product);
        	}
    	} finally {
    		readWriteLock.writeLock().unlock();
    	}
    	return maxId - 1;
    }

    @Override
    public void delete(Long id) {
    	if(id == null) {
			throw new IllegalArgumentException("Id is null");
		}

    	try {
    		readWriteLock.writeLock().lock();
        	products.removeIf(p -> id.equals(p.getId()));
    	} finally {
    		readWriteLock.writeLock().unlock();
    	}
    }

}
