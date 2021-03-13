package ru.job4j.todo.servlets;

import org.json.JSONObject;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Class RegServlet
 * Сервлет выполняет переход на страницу регистрации пользователя.
 * А также осуществляет сохранение пользователя в базе данных.
 * @author Dmitry Razumov
 * @version 1
 */
@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RegServlet.class.getName());

    private static final UserService SERVICE = new UserService(new HbmStore());

    /**
     * Метод переадресует запрос на страницу регистрации пользователя.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.html").forward(req, resp);
    }

    /**
     * Метод принимает регистрационные данные пользователя и сохраняет их в базе данных.
     * Затем осуществляет переадресацию на страницу авторизации.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String json = "";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8)
        )) {
            json = br.readLine();
            LOG.info("Получены регистрационные данные: {}", json);
        } catch (IOException ex) {
            LOG.error("Ошибка получения данных.", ex);
        }
        JSONObject jsonObj = new JSONObject(json);
        String name = jsonObj.getString("name");
        String email = jsonObj.getString("email");
        String password = jsonObj.getString("password");
        if (SERVICE.findUser(email) != null) {
            LOG.info("Пользователь с email: {} уже существует.", email);
            PrintWriter out = resp.getWriter();
            out.print("Пользователь с таким email уже существует. Введите другой email.");
            out.flush();
        } else {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            LOG.info("Создан пользователь {}", user);
            user = SERVICE.saveUser(user);
            LOG.info("Пользователь {} сохранен в БД.", user);
        }
    }
}
