package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String API_POST = "/api/posts";
    private static final String API_POST_ID = "/api/posts/\\d+";
    private static final String METHOD_DELETE = "/api/posts/\\d+";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    @Override
    public void init() {
        // отдаём класс конфигурации
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);

        // получаем по имени бина
        controller = (PostController) context.getBean("postController");
    }
    public Long getPostId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(METHOD_GET) && path.equals(API_POST)) {
                controller.all(resp);
                return;
            }
            if (method.equals(METHOD_GET) && path.matches(API_POST_ID)) {
                // easy way
                final var id = getPostId(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(METHOD_POST) && path.equals(API_POST)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(METHOD_DELETE) && path.matches(API_POST_ID)) {
                // easy way
                final var id = getPostId(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

