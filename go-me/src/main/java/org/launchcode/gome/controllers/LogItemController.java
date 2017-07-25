package org.launchcode.gome.controllers;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.User;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.launchcode.gome.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */

@Controller
@RequestMapping("go-me")
public class LogItemController {

    @Autowired
    private LogItemDao logItemDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    //main page - add an item
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addItem(Model model) {
        model.addAttribute("title", "goMe");
        model.addAttribute(new LogItem());
        model.addAttribute("categories", categoryDao.findAll());

        return "index/add-item";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid LogItem logItem, Errors errors,
                          @RequestParam int categoryId, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            return "index/add-item";
        }

        Category category = categoryDao.findOne(categoryId);
        logItem.setCategory(category);
        logItemDao.save(logItem);
        return "redirect:/go-me/done-list";
    }

    //display simple donelist
    @RequestMapping(value="done-list", method = RequestMethod.GET)
    public String doneList(Model model) {
        model.addAttribute("title", "goMe");
        model.addAttribute(new LogItem());
        model.addAttribute("logItems", logItemDao.findAll());
        return "index/done-list";
    }

    //remove logged items
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String removeLoggedItems(Model model) {
        model.addAttribute("logItems", logItemDao.findAll());
        model.addAttribute("title", "goMe");
        return "index/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String removeLoggedItems(@RequestParam int[] logItemIds) {

        for (int logItemId : logItemIds) {
            logItemDao.delete(logItemId);
        }

        return "redirect:/go-me/done-list";
    }

}
