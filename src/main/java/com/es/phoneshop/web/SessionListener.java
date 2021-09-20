package com.es.phoneshop.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.model.viewsHistory.UserViewsHistory;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
	

    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent se)  { 
    	se.getSession().setAttribute(ConstantStrings.recentlyViewed, new UserViewsHistory());
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    }
	
}