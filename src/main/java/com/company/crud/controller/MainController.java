package com.company.crud.controller;


import com.company.crud.domain.User;
import com.company.crud.repos.UserRepo;
import com.company.crud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 10;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Value("${msg.title}")
    private String title;


    @GetMapping("/")
    public String auth(Map<String, Object> model){
        model.put("title", title);
        return  "auth";
    }

    @GetMapping(value = "/users")
    public String getUsers(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<User> users = userService.findAll(pageNumber, ROW_PER_PAGE);

        long count = userService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("users", users);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "users";
    }


    @GetMapping(value = "/users/{userId}")
    public String getUserById(Model model, @PathVariable long userId) {
        User user = null;
        try {
            user = userService.findById(userId);
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("user", user);
        return "user";
    }


    @PostMapping(value="filter")
    public String filter( @RequestParam String filter, Map<String, Object> model){
        Iterable<User> users;
        if(filter !=null && !filter.isEmpty()){
             users = userRepo.findByPersonId(filter);
        }else{
            users = userRepo.findAll();
        }
        model.put("users", users);

        logger.info("The User successful filtered. User details:  " + users);
        return "users";
    }

    @GetMapping(value = {"/users/add"})
    public String showAddUser(Model model) {
        User user = new User();
        model.addAttribute("add", true);
        model.addAttribute("user", user);

        return "user-edit";
    }



    @PostMapping(value = "/users/add")
    public String addUser(Model model,
                          @ModelAttribute("user") User user) {
        try {
            User newUser = userService.save(user);
            return "redirect:/users/" + newUser.getId();
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            model.addAttribute("user", user);
            return "user-edit";
        }
    }

    @GetMapping(value = {"/users/{userId}/edit"})
    public String showEditUser(Model model, @PathVariable long userId) {
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping(value = {"/users/{userId}/edit"})
    public String updateUser(Model model,
                             @PathVariable long userId,
                             @ModelAttribute("user") User user) {
        try {
            user.setId(userId);
            userService.update(user);
            return "redirect:/users/" + user.getId();
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "user-edit";
        }
    }
    @GetMapping(value = {"/users/{userId}/delete"})
    public String showDeleteUser(
            Model model, @PathVariable long userId) {
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping(value = {"/users/{userId}/delete"})
    public String deleteUserById(
            Model model, @PathVariable long userId) {
        try {
            userService.deleteById(userId);
            return "redirect:/users";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "user";
        }
    }
}