package ru.project.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.model.Meal;
import ru.project.model.Restaurant;
import ru.project.util.Util;
import ru.project.web.meal.MealController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("user/restaurant")
public class RestaurantController extends AbstractRestaurantController {

    @Autowired
    private MealController mealController;

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/restaurantForm";
    }

    @PostMapping
    public String createOrUpdateRestaurant(HttpServletRequest request) {
        String newDescription = request.getParameter("description");
        String otherMeal = request.getParameter("otherMeal");
        String newPrice = request.getParameter("price");
        String restaurantId = request.getParameter("id");
        Restaurant restaurant;
        if (!StringUtils.hasLength(restaurantId)) {
            restaurant = super.create(new Restaurant(request.getParameter("name")));
        } else {
            restaurant = super.get(getId(request));
            for (Meal meal : Util.getFilteredMeals(mealController.getRestaurantMeals(restaurant.getId()), Meal::isCurrent)) {
                String mealId = request.getParameter("mealId" + "_" + meal.getId());
                String description = request.getParameter("description" + "_" + mealId);
                String price = request.getParameter("price" + "_" + mealId);
                String check = Optional.ofNullable(request.getParameter("check" + "_" + mealId)).orElse("");
                meal.setDescription(description);
                meal.setPrice(Integer.valueOf(price));
                if (StringUtils.hasLength(check)) {
                    meal.setCurrent(false);
                }
                mealController.update(meal, meal.id(), getId(request));
            }
            super.update(restaurant, getId(request));
        }
        if (StringUtils.hasLength(newDescription) & StringUtils.hasLength(newPrice)) {
            Meal meal = new Meal(newDescription, Integer.valueOf(newPrice));
            mealController.create(meal, restaurant.getId());
        }
        if (StringUtils.hasLength(otherMeal)) {
            Meal meal = mealController.get(Integer.parseInt(otherMeal), getId(request));
            meal.setCurrent(true);
            mealController.update(meal, Integer.parseInt(otherMeal), getId(request));
        }
        return "redirect:profile";
    }

    @GetMapping("all")
    public String getAllRestaurants(Model model) {
        model.addAttribute("restaurants", super.getAll());
        return "restaurant/restaurantsVoting";
    }

    @GetMapping("/update/{restaurantId}")
    public String update(Model model, @PathVariable int restaurantId) {
        Restaurant restaurant = super.get(restaurantId);
        model.addAttribute("currentMeals", Util.getFilteredMeals(mealController
                        .getRestaurantMeals(restaurant.getId()),
                Meal::isCurrent));
        model.addAttribute("otherMeals", Util.getFilteredMeals(mealController
                        .getRestaurantMeals(restaurant.getId()),
                meal -> !meal.isCurrent()));
        model.addAttribute("restaurant", restaurant);
        return "restaurant/restaurantForm";
    }

    @GetMapping("/delete/{restaurantId}")
    public String deleteRestaurant(@PathVariable int restaurantId) {
        super.delete(restaurantId);
        return "redirect:/user/profile";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
