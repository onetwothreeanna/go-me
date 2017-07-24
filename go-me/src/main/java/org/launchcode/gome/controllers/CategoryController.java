package org.launchcode.gome.controllers;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
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

    //add a category, show list of categories
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("title", "goMe");
        model.addAttribute(new Category());
        model.addAttribute("categories", categoryDao.findAll());
        return "category/index";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute @Valid Category category, Errors errors, Model model)
    {
        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            return "category/index";
        }

        categoryDao.save(category);
        return "redirect:/category";
    }

    //remove categories (empty first)
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String removeCategories(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Remove Category");
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

}
