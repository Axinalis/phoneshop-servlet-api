package com.es.phoneshop.web;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

	@Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private ProductDao productDao;
	
    ProductDetailsPageServlet page;
    
    @Before
    public void setup() throws ServletException{
    	page = new ProductDetailsPageServlet();
        //when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        page.init(config);
    }
    
	@Test
	public void testDoGet() {
		try {
			page.doGet(request, response);
			fail("There was no exception");
		} catch (ServletException | IOException e) {
			fail("Not correct exception");
		} catch (ProductNotFoundException ex) {
			assertEquals(ex.getMessage(), "Id is not valid");
		}
		
		
	}

}
