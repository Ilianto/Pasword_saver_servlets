package com.vsu.filter;

import com.vsu.utils.URLUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter("/user/*")
public class LoginFilter implements Filter {
    private static Logger LOGGER = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.log(Level.INFO, "Вы попали в логин фильтр");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession().getAttribute("profile") == null) {
            response.sendRedirect(URLUtils.getFullUrlForRedirect(request, "/"));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
