package com.vsu.utils;

import jakarta.servlet.http.HttpServletRequest;

public class URLUtils {
    public static String getFullUrlForRedirect(HttpServletRequest request, String baseUrl) {
        return request.getContextPath() + baseUrl;
    }
}
