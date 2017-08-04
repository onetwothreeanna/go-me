package org.launchcode.gome.controllers;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.User;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.launchcode.gome.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private LogItemDao logItemDao;
    @Autowired
    private UserDao userDao;

    //add a category, show list of categories
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addCategory(Model model, HttpServletRequest request) {
        model.addAttribute(new Category());
        model.addAttribute("title", "goMe");

        String username = request.getSession().getAttribute("currentUser").toString();
        int currentUserId = userDao.findByUsername(username).getId();
        model.addAttribute("categories", categoryDao.findByUserId(currentUserId));
        return "category/index";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute @Valid Category category, Errors errors, Model model, HttpServletRequest request)
    {
        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            model.addAttribute("categories", categoryDao.findAll());

            return "category/index";
        }

        String username = request.getSession().getAttribute("currentUser").toString();
        User currentUser = userDao.findByUsername(username);
        category.setUser(currentUser);
        categoryDao.save(category);
        return "redirect:/category";
    }


    //remove categories (empty first)
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String removeCategories(Model model, HttpServletRequest request) {
        model.addAttribute("categories", categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));
        model.addAttribute("title", "goMe");
        return "category/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String removeCategories(@RequestParam int[] categoryIds) {

        for (int categoryId : categoryIds) {
            Category category = categoryDao.findOne(categoryId);

            List<LogItem> logItems = category.getLogItems();
            if (logItems.size() > 0) {

                for (LogItem logItem : logItems) {
                    logItemDao.delete(logItem.getId());
                }
                categoryDao.delete(categoryId);

            } else {
                categoryDao.delete(categoryId);
            }
        }

        return "redirect:/category";
    }



    // view handler
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public String viewCategory(Model model, @PathVariable int categoryId, HttpServletRequest request){
        Category category = categoryDao.findOne(categoryId);
        User user = userDao.findByUsername(request.getSession().getAttribute("currentUser").toString());
        //If current user matches path variable category user, show.  Otherwise, redirect with error.
        if (category.getUser() == user) {
            model.addAttribute("title", "goMe");
            model.addAttribute("category", category);
            model.addAttribute(new LogItem());
            model.addAttribute("categories", categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));

            return "category/view-by-category";
        }else{

            model.addAttribute(new Category());
            model.addAttribute("title", "goMe");
            model.addAttribute("categories", categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));
            model.addAttribute("error", "HEY! You are not authorized to view that content. Try one of these links instead.");
            return "category/index";

        }
    }

    @RequestMapping(value="/{categoryId}", method = RequestMethod.POST)
    public String viewCategoryAddItem(@ModelAttribute @Valid LogItem logItem, Errors errors,
                          @PathVariable int categoryId, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Category category = categoryDao.findOne(categoryId);
        User user = category.getUser();

        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            model.addAttribute("category", category);
            model.addAttribute(logItem);
            model.addAttribute("categories", categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));

            return "category/view-by-category";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E - MM/dd/yyyy - HH:mm a");
        String dateTime = now.format(formatter);
        logItem.setDateTime(dateTime);

        logItem.setUser(user);
        logItem.setCategory(category);
        logItemDao.save(logItem);

        return "redirect:/category/{categoryId}";
    }
}
