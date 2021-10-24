package com.es.phoneshop.web.filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class DosFilterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    private DosFilter filter = new DosFilter();

    @Before
    public void setup1() throws ServletException {
        filter.init(null);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        when(request.getRemoteAddr()).thenReturn("someIP");
    }

    @Test
    public void testDoFilterOneIP() throws ServletException, IOException {
        for(int i=0;i<50;i++){
            filter.doFilter(request, response, chain);
        }
        verify(chain, times(21)).doFilter(request, response);
        verify((response), times(29)).setStatus(429);
    }

    @Test
    public void testDoFilterDifferentIP() throws ServletException, IOException {
        for(int i=0;i<50;i++){
            when(request.getRemoteAddr()).thenReturn("someIP_" + i);
            filter.doFilter(request, response, chain);
        }
        verify(chain, times(50)).doFilter(request, response);
    }

}
