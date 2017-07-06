package org.launchcode.gome.controllers;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */




@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    //add a category, show list of categories
    @RequestMapping(value="", method = RequestMethod.GET)
    public String addItem(Model model) {
        model.addAttribute("title", "goMe");
        model.addAttribute(new Category());
        model.addAttribute("categories", categoryDao.findAll());
        return "category/addCategory";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid Category category, Errors errors, Model model)
    {
        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            return "category/addCategory";
        }

        categoryDao.save(category);
        return "redirect:/category";
    }

}
