package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.models.Category;
import ru.job4j.todo.models.Item;
import ru.job4j.todo.models.User;
import ru.job4j.todo.services.CategoryService;
import ru.job4j.todo.services.ItemService;
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
import java.util.List;

/**
 * Class ItemServlet
 * Сервлет осуществляет вывод списка заданий. А также сохранение и обновление заданий.
 * @author Dmitry Razumov
 * @version 1
 */
@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    /**
     * Поле содержит логер для записи информации в лог-файл.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ItemServlet.class.getName());

    private static final ItemService ITEM_SERVICE = new ItemService(new HbmStore());

    private static final CategoryService CATEGORY_SERVICE = new CategoryService(new HbmStore());

    /**
     * Метод возвращает список заданий.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        String find = req.getParameter("find");
        List<Item> items = null;
        if (find.equals("all")) {
            items = ITEM_SERVICE.findItemsForUser(user);
        } else {
            items = ITEM_SERVICE.findItemsIsDoneForUser(Boolean.parseBoolean(find), user);
        }
        LOG.info("Получено {} заданий для пользователя {}", items.size(), user);
        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        for (Item item : items) {
            json.put(String.valueOf(item.getNumber()), gson.toJson(item));
        }
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    /**
     * Метод сохраняет либо обновляет задание.
     * @param req Входящий запрос.
     * @param resp Исходящий запрос.
     * @throws ServletException Исключение.
     * @throws IOException Исключение.
     */
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        if (id == null) {
            String jsonStr = "";
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8)
            )) {
                jsonStr = br.readLine();
                LOG.info("Получены данные: {}", jsonStr);
            } catch (IOException ex) {
                LOG.error("Ошибка получения данных.", ex);
            }
            JSONObject json = new JSONObject(jsonStr);
            String desc = json.getString("desc");
            int num = ITEM_SERVICE.number(user);
            Item item = Item.of(desc, user, num + 1);
            for (var cId : json.getJSONArray("cats")) {
                Category category = CATEGORY_SERVICE.findCategory(
                        Integer.parseInt(String.valueOf(cId))
                );
                item.addCategory(category);
            }
            LOG.info("Создано задание {}", item);
            item = ITEM_SERVICE.saveItem(item);
            LOG.info("Задание {} сохранено в БД.", item);
        } else {
            LOG.info("Задание № {} выполнено.", id);
            Item item = ITEM_SERVICE.findItem(Integer.parseInt(id));
            item.setDone(true);
            if (ITEM_SERVICE.updateItem(item)) {
                LOG.info("Задание {} в базе обновлено.", item);
            }
        }
    }
}
