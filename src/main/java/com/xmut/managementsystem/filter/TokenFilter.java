package com.xmut.managementsystem.filter;

import com.xmut.managementsystem.exception.TokenAuthExpiredException;
import com.xmut.managementsystem.tool.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@WebFilter(filterName = "TokenFilter")
public class TokenFilter implements Filter {
    @Autowired
    private TokenService tokenService;

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        if (token != null)
        {
            if (!tokenService.check((token))) {
                throw new TokenAuthExpiredException("登录已过期");
            }
        }
        chain.doFilter(request, response);
    }

}
