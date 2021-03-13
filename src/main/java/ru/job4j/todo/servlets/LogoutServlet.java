package ru.job4j.todo.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class LogoutServlet
 * Сервлет осуществляет выход пользователя из системы создания заданий.
 * @author Dmitry Razumov
 * @version 1
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class.getName());

    /**
     * Метод удаляет данные пользователя из сессии и переадресует запрос на страницу авторизации.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        User user = (User) req.getSession().getAttribute("user");
        LOG.info("Пользователь {} вышел из системы.", user);
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect("login.html");
    }
}
