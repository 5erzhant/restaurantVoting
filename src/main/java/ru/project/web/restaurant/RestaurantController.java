package ru.project.web.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.model.Restaurant;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("user/restaurant")
public class RestaurantController extends AbstractRestaurantController {

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/restaurantForm";
    }

    @PostMapping
    public String createOrUpdateRestaurant(HttpServletRequest request) {
        Restaurant restaurant = new Restaurant(request.getParameter("name"));
        if (request.getParameter("id").isEmpty()) {
            super.create(restaurant);
        } else {
            super.update(restaurant, getId(request));
        }
        return "redirect:profile";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
