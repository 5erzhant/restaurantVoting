package ru.project.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;
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
                request.getRequestDispatcher("/restaurant/restaurantForm.jsp").forward(request, response);
            }
            case "delete" -> {
                restaurantController.delete(getId(request));
                response.sendRedirect("users");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String newDescription = request.getParameter("description");
        String newPrice = request.getParameter("price");
        String restaurantId = request.getParameter("id");
        Restaurant restaurant;
        if (!StringUtils.hasLength(restaurantId)) {
            restaurant = restaurantController.create(new Restaurant(request.getParameter("name")));
        } else {
            restaurant = restaurantController.get(Integer.parseInt(restaurantId));
            for (Meal meal : restaurant.getMealList()) {
                String mealId = request.getParameter("mealId" + "_" + meal.getId());
                String description = request.getParameter("description" + "_" + mealId);
                String price = request.getParameter("price" + "_" + mealId);
                meal.setDescription(description);
                meal.setPrice(Integer.valueOf(price));
            }
            restaurantController.update(restaurant);
        }

        if (StringUtils.hasLength(newDescription) & StringUtils.hasLength(newPrice)) {
            Meal meal = new Meal(newDescription, Integer.valueOf(newPrice));
            mealController.create(meal, restaurant.getId());
        }
        response.sendRedirect("users");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
