package com.es.phoneshop.web.listeners;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

import static com.es.phoneshop.constant.ConstantStrings.*;

@WebListener
public class SessionListener implements HttpSessionListener {

    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent se)  { 
    	se.getSession().setAttribute(RECENTLY_VIEWED, new UserViewsHistory());
    	se.getSession().setAttribute(STRING_SESSION_ATTRIBUTE_CART, new Cart());
    	se.getSession().setAttribute(MINI_CART, new ArrayList<CartItem>());
    	se.getSession().setAttribute(STRING_SESSION_ATTRIBUTE_ORDER, new ArrayList<Order>());
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    }
	
}
