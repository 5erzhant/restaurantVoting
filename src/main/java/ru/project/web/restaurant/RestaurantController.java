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
import ru.project.web.votingHistory.VotingHistoryController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("user/restaurant")
public class RestaurantController extends AbstractRestaurantController {

    @Autowired
    private MealController mealController;

    @Autowired
    private VotingHistoryController votingHistoryController;

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
        int restaurantId = Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
        Restaurant restaurant;
        if (!StringUtils.hasLength(String.valueOf(restaurantId))) {
            super.create(new Restaurant(request.getParameter("name")));
        } else {
            restaurant = super.get(restaurantId);
            restaurant.setName(request.getParameter("name"));
            for (Meal meal : Util.getFilteredMeals(mealController.getRestaurantMeals(restaurantId), Meal::isCurrent)) {
                String mealId = request.getParameter("mealId" + "_" + meal.getId());
                String description = request.getParameter("description" + "_" + mealId);
                String price = request.getParameter("price" + "_" + mealId);
                String check = Optional.ofNullable(request.getParameter("check" + "_" + mealId)).orElse("");
                meal.setDescription(description);
                meal.setPrice(Integer.valueOf(price));
                if (StringUtils.hasLength(check)) {
                    meal.setCurrent(false);
                }
                mealController.update(meal, meal.id(), restaurantId);
            }
            super.update(restaurant, restaurantId);
        }
        if (StringUtils.hasLength(newDescription) & StringUtils.hasLength(newPrice)) {
            Meal meal = new Meal(newDescription, Integer.valueOf(newPrice));
            mealController.create(meal, restaurantId);
        }
        if (StringUtils.hasLength(otherMeal)) {
            Meal meal = mealController.get(Integer.parseInt(otherMeal), restaurantId);
            meal.setCurrent(true);
            mealController.update(meal, Integer.parseInt(otherMeal), restaurantId);
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
                        .getRestaurantMeals(restaurantId),
                Meal::isCurrent));
        model.addAttribute("otherMeals", Util.getFilteredMeals(mealController
                        .getRestaurantMeals(restaurantId),
                meal -> !meal.isCurrent()));
        model.addAttribute("restaurant", restaurant);
        return "restaurant/restaurantForm";
    }

    @GetMapping("/delete/{restaurantId}")
    public String deleteRestaurant(@PathVariable int restaurantId) {
        super.delete(restaurantId);
        return "redirect:/user/profile";
    }

    @GetMapping("/history/{restaurantId}")
    public String getHistory(Model model, @PathVariable int restaurantId) {
        model.addAttribute("votingHistory", votingHistoryController.getRestaurantVotingHistory(restaurantId));
        return "restaurant/restaurantVotingHistory";
    }

    @GetMapping("/vote/{restaurantId}")
    public String vote(@PathVariable int restaurantId) {
        if (Util.isTooLate()) {
            return "restaurant/tooLatePage";
        }
        votingHistoryController.vote(restaurantId);
        return "redirect:/user/profile";
    }
}
