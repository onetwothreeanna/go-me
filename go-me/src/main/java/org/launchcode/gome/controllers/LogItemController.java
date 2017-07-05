package org.launchcode.gome.controllers;

//import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.data.LogItemDao;
//import org.launchcode.gome.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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

    //main page - add an item
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addItem(Model model) {
        model.addAttribute("title", "goMe");
        model.addAttribute(new LogItem());
        return "index/addItem";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid LogItem logItem, Errors errors, Model model)
    {
        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            return "index/addItem";
        }

        logItemDao.save(logItem);
        return "redirect:log/donelist";
    }

    //display simple donelist
    @RequestMapping(value="donelist", method = RequestMethod.GET)
    public String doneList(Model model) {
        model.addAttribute("title", "done list");
        model.addAttribute(new LogItem());
        model.addAttribute("logItems", logItemDao.findAll());
        return "index/doneList";
    }


}
