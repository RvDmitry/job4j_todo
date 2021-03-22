package ru.job4j.todo.servlets;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.models.Category;
import ru.job4j.todo.models.User;
import ru.job4j.todo.services.CategoryService;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class CategoryServlet
 * Сервлет выполняет загрузку категорий из хранилища.
 * А также сохраняет категорию в хранилище.
 * @author Dmitry Razumov
 * @version 1
 */
@WebServlet("/cats")
public class CategoryServlet extends HttpServlet {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryServlet.class.getName());

    private static final CategoryService SERVICE = new CategoryService(new HbmStore());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        List<Category> categories = SERVICE.findCategoriesForUser(user);
        LOG.info("Загружен список категорий: {}", categories);
        JSONObject json = new JSONObject();
        json.put("0", user.getName());
        categories.forEach(cat -> json.put(String.valueOf(cat.getId()), cat.getName()));
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String cat = req.getParameter("cat");
        LOG.info("Создана новая категория: {}", cat);
        Category category = Category.of(cat, user);
        category = SERVICE.saveCategory(category);
        LOG.info("Категория {} сохранена в БД.", category);
    }
}
