package com.leandropitta.budget_manager.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class OriginCheckInterceptor implements HandlerInterceptor {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "https://livia.leandropitta.com.br",
            "https://15.228.249.145/"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String origin = request.getHeader("Origin");

        if (ALLOWED_ORIGINS.contains(origin)) {
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"this application only works with your frontend\"}");
            return false;
        }
    }
}
