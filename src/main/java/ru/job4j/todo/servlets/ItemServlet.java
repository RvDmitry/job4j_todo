package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.models.Item;
import ru.job4j.todo.services.ItemService;
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

    private static final ItemService SERVICE = new ItemService(new HbmStore());

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
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String find = req.getParameter("find");
        List<Item> items = null;
        if (find.equals("all")) {
            items = SERVICE.findItems();
        } else {
            items = SERVICE.findItemsIsDone(Boolean.parseBoolean(find));
        }
        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        for (Item item : items) {
            json.put(String.valueOf(item.getId()), gson.toJson(item));
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
        String desc = req.getParameter("desc");
        String id = req.getParameter("id");
        if (id == null) {
            LOG.info("Получено описание дела: {}", desc);
            Item item = new Item(desc);
            LOG.info("Создано дело {}", item);
            item = SERVICE.saveItem(item);
            LOG.info("Дело {} записано в БД", item);
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(item));
            out.flush();
        } else {
            LOG.info("Дело № {} выполнено.", id);
            Item item = SERVICE.findItem(Integer.parseInt(id));
            item.setDone(true);
            if (SERVICE.updateItem(item)) {
                LOG.info("Дело {} в базе обновлено.", item);
            }
        }
    }
}
