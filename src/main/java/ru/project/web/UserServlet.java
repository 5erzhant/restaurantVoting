package ru.project.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.project.model.User;
import ru.project.web.user.UserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private UserController userController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        userController = springContext.getBean(UserController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "create", "update" -> {
                User user = "create".equals(action) ? new User() : userController.get(SecurityUtil.authUserId());
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
            }
            case "delete" -> {
                userController.delete();
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        User user;
        if (StringUtils.hasLength(userId)) {
            SecurityUtil.setAuthUserId(Integer.parseInt(userId));
            user = userController.get(Integer.parseInt(userId));
        } else {
            user = new User(
                    request.getParameter("name"),
                    request.getParameter("email"),
                    request.getParameter("password"));
            if (StringUtils.hasLength(request.getParameter("id"))) {
                userController.update(user);
            } else {
                SecurityUtil.setAuthUserId(userController.create(user).getId());
            }
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/userPage.jsp").forward(request, response);
    }
}
