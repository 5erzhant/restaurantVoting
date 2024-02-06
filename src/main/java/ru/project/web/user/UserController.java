package ru.project.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.project.model.User;
import ru.project.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractUserController {

    @GetMapping
    public String root() {
        return "index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "user/userForm";
    }

    @GetMapping("/update")
    public String update(Model model) {
        model.addAttribute("user", super.get(SecurityUtil.authUserId()));
        return "user/userForm";
    }

    @GetMapping("/delete")
    public String deleteUser() {
        super.delete();
        return "redirect:/user";
    }

    @PostMapping("/set")
    public String setUser(HttpServletRequest request) {
        int userId = getId(request);
        SecurityUtil.setAuthUserId(userId);
        return "redirect:profile";
    }

    @GetMapping("/profile")
    public String getUser(Model model) {
        model.addAttribute("user", super.getWithRestaurants(SecurityUtil.authUserId()));
        return "user/userPage";
    }

    @PostMapping
    public String createOrUpdateUser(HttpServletRequest request) {
        User user = new User(request.getParameter("name"), request.getParameter("email"),
                request.getParameter("password"));
        if (request.getParameter("id").isEmpty()) {
            super.create(user);
        } else {
            super.update(user, getId(request));
        }
        return "redirect:user/profile";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
