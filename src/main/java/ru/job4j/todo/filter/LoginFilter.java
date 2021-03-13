package ru.job4j.todo.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class LoginFilter
 * Класс-фильтр, осуществляет фильтрацию запросов.
 * Если в запросе содержаться данные пользователя, то переадресует на страницу создания заданий.
 * Если запрос без данных о пользователе, то переадресует на страницу авторизации либо регистрации.
 * @author Dmitry Razumov
 * @version 1
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean isLoginPage = uri .endsWith("/login") || uri.endsWith("/reg");
        if (loggedIn || isLoginPage) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
