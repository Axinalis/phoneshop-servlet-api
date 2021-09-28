package com.es.phoneshop.web.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;

import java.util.ArrayList;

import static com.es.phoneshop.constant.ConstantStrings.RECENTLY_VIEWED;
import static com.es.phoneshop.constant.ConstantStrings.STRING_SESSION_ATTRIBUTE_CART;

@WebListener
public class SessionListener implements HttpSessionListener {

    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent se)  { 
    	se.getSession().setAttribute(RECENTLY_VIEWED, new UserViewsHistory());
    	se.getSession().setAttribute(STRING_SESSION_ATTRIBUTE_CART, new Cart());
    	//se.getSession().setAttribute("miniCart", new ArrayList<CartItem>());
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    }
	
}
