package ru.job4j.todo.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.models.User;
import ru.job4j.todo.services.UserService;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class LoginServlet
 * Сервлет осуществляет проверку данных вводимых при авторизации.
 * Если данные верны, то осуществляет переход на страницу заданий.
 * @author Dmitry Razumov
 * @version 1
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class.getName());

    private static final UserService SERVICE = new UserService(new HbmStore());

    /**
     * Метод осуществляет переадресацию на страницу авторизации.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    /**
     * Метод осуществляет проверку вводимых пользователем данных.
     * Если данные верны, то переадресует на страницу создания заданий.
     * Иначе, возвращает на страницу авторизации.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        LOG.info("Получен email {}, пароль {}", email, password);
        User user = SERVICE.findUser(email);
        if (user == null) {
            LOG.info("Пользователь с email {} не найден.", email);
            doGet(req, resp);
        } else if (user.getPassword().equals(password)) {
            LOG.info("Успешная аутентификация пользователя {}", user);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            LOG.info("Неверный пароль пользователя {}.", user);
            doGet(req, resp);
        }
    }
}
