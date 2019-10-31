package com.company.crud;

import com.company.crud.domain.User;
import com.company.crud.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return  "main";
    }

    @PostMapping("/main")
    public String add(
            @RequestParam String personId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth,
            @RequestParam String familyStatus,
            @RequestParam String education,
            Map<String, Object> model){
        User user = new User(personId,firstName,lastName, dateOfBirth,familyStatus, education);
        userRepo.save(user);
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping("filter")
    public String filter( @RequestParam String filter, Map<String, Object> model){
        Iterable<User> users;
        if(filter !=null && !filter.isEmpty()){
             users = userRepo.findByPersonId(filter);
        }else{
            users = userRepo.findAll();
        }
        model.put("users", users);
        return "main";
    }


}