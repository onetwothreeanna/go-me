package org.launchcode.gome.controllers;

//import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
//import org.launchcode.gome.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */
@Controller
@RequestMapping("log")
public class LogItemController {

    @Autowired
    private LogItemDao logItemDao;

//    @Autowired
//    private CategoryDao categoryDao;
//
//    @Autowired
//    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "goMe");
        return "index/addItem";
    }




}
