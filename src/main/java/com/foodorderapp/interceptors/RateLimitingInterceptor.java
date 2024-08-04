package com.foodorderapp.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final Map<String, Long> loginAttempts = new ConcurrentHashMap<>();

    private static final int MAX_LOGIN_ATTEMPTS = 10;
    private static final long RATE_LIMIT_PERIOD_MS = 60 * 1000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String username = request.getParameter("email");

        if (username != null) {
            long currentTime = System.currentTimeMillis();
            synchronized (loginAttempts) {
                loginAttempts.entrySet().removeIf(entry -> currentTime - entry.getValue() > RATE_LIMIT_PERIOD_MS);

                if (loginAttempts.getOrDefault(username, 0L) >= MAX_LOGIN_ATTEMPTS) {
                    response.setStatus(429);
                    response.getWriter().write("Too many login attempts. Please try again later.");
                    return false;
                }

                loginAttempts.put(username, currentTime);
            }
        }

        return true;
    }
}