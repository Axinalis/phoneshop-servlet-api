package com.es.phoneshop.web.filters;

import com.es.phoneshop.service.DosProtectionService;
import com.es.phoneshop.service.impl.DefaultDosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private DosProtectionService dosProtection;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosProtection = DefaultDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(dosProtection.isAllowed(request.getRemoteAddr())){
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)request).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
