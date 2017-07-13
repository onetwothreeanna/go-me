package org.launchcode.gome.controllers;

import org.launchcode.gome.models.Login;
import org.launchcode.gome.models.User;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.launchcode.gome.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by AnnaYoungyeun on 6/5/17.
 */
@Controller
@RequestMapping("user")
@SpringBootApplication
public class UserController {

    @Autowired
    private LogItemDao logItemDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //add new user
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new User());
        model.addAttribute("title", "Register");
        return "user/add";
    }

    @RequestMapping(value = "add", method = POST)
    public String add(@ModelAttribute @Valid User user, Errors errors, Model model, String verify) {

        if (errors.hasErrors()){
            model.addAttribute("title", "Register");
            model.addAttribute(user);
            return "user/add";
        }

        model.addAttribute("user", user);
        boolean passwordsMatch = true;
        if (user.getPassword() == null || verify == null
                || !user.getPassword().equals(verify)) {
            passwordsMatch = false;
            model.addAttribute("verifyError", "Passwords must match");
        }

        if (passwordsMatch) {
            userDao.save(user);
            return "user/index";
        }
        return "user/add";

    }


    //login
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute(new Login());
        model.addAttribute("title", "Login");
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid Login loginAttempt, Errors errors, Model model) {

        if (errors.hasErrors()){
            model.addAttribute("title", "Login");
            model.addAttribute(loginAttempt);
            return "user/login";
        }

        model.addAttribute("login", loginAttempt);
        //look for username in database
        User user = userDao.findByUsername(loginAttempt.getUsername());

        //if there, get the user object.
        if (user != null) {
            if (userDao.exists(user.getId())) {
                model.addAttribute("user", user);

                //check password
                boolean passwordsMatch = user.getPassword().equals(loginAttempt.getPassword());
                if (!passwordsMatch) {
                    model.addAttribute("verifyError", "Username or password is incorrect.");
                }

                if (passwordsMatch) {
                    return "user/index";
                }
            }
        }

        //if not there, show login form again with error.
        model.addAttribute("title", "Login");
        model.addAttribute("verifyError", "Username or password is incorrect.");
        return "user/login";

    }

}

