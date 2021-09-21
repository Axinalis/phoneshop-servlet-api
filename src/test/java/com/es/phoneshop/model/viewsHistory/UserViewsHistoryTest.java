package com.es.phoneshop.model.viewsHistory;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserViewsHistoryTest {

    private ProductDao dao = ArrayListProductDao.getInstance();
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private UserViewsHistory hist;

    @Before
    public void setup(){
        Currency usd = Currency.getInstance("USD");
        product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg");
        product2 = new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        product3 = new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        product4 = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg");
        hist = new UserViewsHistory();
    }

    @Test
    public void test(){
        Deque<Product> deque1 = new LinkedList<Product>();
        deque1.add(product3);
        deque1.add(product4);
        deque1.add(product2);

        hist.addProduct(product1);
        hist.addProduct(product2);
        hist.addProduct(product3);
        //There must be 3 last products in reverse order
        hist.addProduct(product2);
        hist.addProduct(product4);
        hist.addProduct(product3);

        assertEquals(deque1, hist.getRecentlyViewed());

        hist.addProduct(product1);
        //The repeated pages appear on the first place again
        hist.addProduct(product2);
        hist.addProduct(product3);
        hist.addProduct(product4);
        hist.addProduct(product3);

        assertEquals(deque1, hist.getRecentlyViewed());

    }


}
