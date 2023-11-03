package ru.project.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;
import ru.project.model.User;
import ru.project.web.restaurant.RestaurantController;
import ru.project.web.user.UserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.project.util.DateUtil.getDate;

public class UserServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private UserController userController;
    private RestaurantController restaurantController;

    @Override
    public void init() {
        springContext = SpringContext.getContext();
        userController = springContext.getBean(UserController.class);
        restaurantController = springContext.getBean(RestaurantController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User user;
        switch (action == null ? "profile" : action) {
            case "create", "update" -> {
                user = "create".equals(action) ? new User() : userController.get(SecurityUtil.authUserId());
                request.setAttribute("user", user);
                request.getRequestDispatcher("/user/userForm.jsp").forward(request, response);
            }
            case "delete" -> {
                userController.delete();
                response.sendRedirect("/");
            }
            default -> {
                user = userController.get(SecurityUtil.authUserId());
                request.setAttribute("user", user);
                request.setAttribute("restaurants", restaurantController.getUserRestaurants());
                request.getRequestDispatcher("/user/userPage.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String id = request.getParameter("id");
        User user;
        if (StringUtils.hasLength(userId)) {
            SecurityUtil.setAuthUserId(Integer.parseInt(userId));
            user = userController.get(SecurityUtil.authUserId());
        } else {
            user = new User(
                    request.getParameter("name"),
                    request.getParameter("email"),
                    request.getParameter("password"));
            if (StringUtils.hasLength(id)) {
                user.setId(Integer.valueOf(id));
                user.setRegistered(getDate(request.getParameter("registered")));
                userController.update(user, Integer.parseInt(id));
            } else {
                SecurityUtil.setAuthUserId(userController.create(user).getId());
            }
        }
        request.setAttribute("restaurants", restaurantController.getUserRestaurants());
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/userPage.jsp").forward(request, response);
    }
}
