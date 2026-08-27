package com.huade.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huade.common.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器：拦截 /api/*，未登录返回 401（登录接口与 CORS 预检放行）
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // CORS 预检请求放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 登录接口放行
        if (request.getRequestURI().endsWith("/api/auth/login")) {
            return true;
        }
        // 已登录（Session 中存有 admin 用户名）
        if (request.getSession().getAttribute("admin") != null) {
            return true;
        }
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(MAPPER.writeValueAsString(Result.error(401, "未登录，请先登录")));
        return false;
    }
}
