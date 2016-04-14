package com.example.auth;

import com.example.domain.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
    throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        HttpSession session = ((HttpServletRequest) req).getSession();
        User user = (User) session.getAttribute("user");

        if (user == null && !httpRequest.getRequestURI().startsWith("/register/")) {
            httpRequest.getRequestDispatcher(httpRequest.getContextPath() + "/login/").forward(req, resp);
        }
        else {
            filterChain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
