package ru.project.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;
import ru.project.model.Meal;
import ru.project.model.Restaurant;
import ru.project.util.Util;
import ru.project.web.meal.MealController;
import ru.project.web.restaurant.RestaurantController;
import ru.project.web.votingHistory.VotingHistoryController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class RestaurantServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private RestaurantController restaurantController;
    private MealController mealController;
    private VotingHistoryController votingHistoryController;

    @Override
    public void init() {
        springContext = SpringContext.getContext();
        restaurantController = springContext.getBean(RestaurantController.class);
        mealController = springContext.getBean(MealController.class);
        votingHistoryController = springContext.getBean(VotingHistoryController.class);
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
                Restaurant restaurant;
                if (action.equals("create")) {
                    restaurant = new Restaurant();
                } else {
                    restaurant = restaurantController.get(getId(request));
                    request.setAttribute("currentMeals", Util.getFilteredMeals(mealController
                                    .getAll(restaurant.getId()),
                            Meal::isCurrent));
                    request.setAttribute("otherMeals", Util.getFilteredMeals(mealController
                                    .getAll(restaurant.getId()),
                            meal -> !meal.isCurrent()));
                }
                request.setAttribute("restaurant", restaurant);
                request.getRequestDispatcher("/restaurant/restaurantForm.jsp").forward(request, response);
            }
            case "delete" -> {
                restaurantController.delete(getId(request));
                response.sendRedirect("users");
            }
            case "all" -> {
                request.setAttribute("restaurants", restaurantController.getAll());
                request.getRequestDispatcher("restaurant/restaurantsVoting.jsp").forward(request, response);
            }
            case "vote" -> {
                int restaurantId = Integer.parseInt(request.getParameter("id"));
                if (Util.isTooLate()) {
                    request.getRequestDispatcher("restaurant/tooLatePage.jsp").forward(request, response);
                }
                votingHistoryController.vote(restaurantId);
                response.sendRedirect("users");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String newDescription = request.getParameter("description");
        String otherMeal = request.getParameter("otherMeal");
        String newPrice = request.getParameter("price");
        String restaurantId = request.getParameter("id");
        Restaurant restaurant;
        if (!StringUtils.hasLength(restaurantId)) {
            restaurant = restaurantController.create(new Restaurant(request.getParameter("name")));
        } else {
            restaurant = restaurantController.get(Integer.parseInt(restaurantId));
            for (Meal meal : Util.getFilteredMeals(mealController.getAll(restaurant.getId()), Meal::isCurrent)) {
                String mealId = request.getParameter("mealId" + "_" + meal.getId());
                String description = request.getParameter("description" + "_" + mealId);
                String price = request.getParameter("price" + "_" + mealId);
                String check = Optional.ofNullable(request.getParameter("check" + "_" + mealId)).orElse("");
                meal.setDescription(description);
                meal.setPrice(Integer.valueOf(price));
                if (StringUtils.hasLength(check)) {
                    meal.setCurrent(false);
                }
                mealController.update(meal, Integer.parseInt(restaurantId));
            }
            restaurantController.update(restaurant);
        }
        if (StringUtils.hasLength(newDescription) & StringUtils.hasLength(newPrice)) {
            Meal meal = new Meal(newDescription, Integer.valueOf(newPrice));
            mealController.create(meal, restaurant.getId());
        }
        if (StringUtils.hasLength(otherMeal)) {
            Meal meal = mealController.get(Integer.parseInt(otherMeal), Integer.parseInt(restaurantId));
            meal.setCurrent(true);
            mealController.update(meal, Integer.parseInt(restaurantId));
        }
        response.sendRedirect("users");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
