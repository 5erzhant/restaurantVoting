package ru.project.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.project.model.Meal;
import ru.project.model.Restaurant;
import ru.project.web.meal.MealController;
import ru.project.web.restaurant.RestaurantController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RestaurantServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private RestaurantController restaurantController;
    private MealController mealController;

    @Override
    public void init() {
        springContext = SpringContext.getContext();
        restaurantController = springContext.getBean(RestaurantController.class);
        mealController = springContext.getBean(MealController.class);
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
                Restaurant restaurant = "create".equals(action) ? new Restaurant() :
                        restaurantController.get(getId(request));
                request.setAttribute("restaurant", restaurant);
            }
        }
        request.getRequestDispatcher("/restaurant/restaurantForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Restaurant restaurant = restaurantController.create(new Restaurant(request.getParameter("name")));
        Meal meal = new Meal(request.getParameter("description"),
                Integer.valueOf(request.getParameter("price")));
        mealController.create(meal, restaurant.getId());
        response.sendRedirect("/users");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
