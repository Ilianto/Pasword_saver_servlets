package com.vsu.filter;

import com.vsu.utils.URLUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter("/auth/*")
public class AuthFilter implements Filter {
    private static Logger LOGGER = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Игнорируем фильтрацию для logout
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/logout")) {
            // Пропускаем запрос без изменений
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        LOGGER.log(Level.INFO, "Вы попали в фильтр аутентификации");

        if (request.getSession().getAttribute("profile") != null) {
            response.sendRedirect(URLUtils.getFullUrlForRedirect(request, "/user/user"));
            return;
        }

        // Пропускаем запрос дальше, если аутентификации нет
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

