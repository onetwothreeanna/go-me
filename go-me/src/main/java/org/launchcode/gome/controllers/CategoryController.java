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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public String addCategory(Model model) {
        model.addAttribute(new Category());
        model.addAttribute("title", "goMe");
        model.addAttribute("categories", categoryDao.findAll());
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
    public String removeCategories(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
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
    public String viewCategory(Model model, @PathVariable int categoryId){
        Category category = categoryDao.findOne(categoryId);
        model.addAttribute("title", "goMe");
        model.addAttribute("category", category);

        return "category/view-by-category";
    }
}
