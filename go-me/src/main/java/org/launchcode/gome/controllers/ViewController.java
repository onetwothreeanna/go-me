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
import java.util.List;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */

@Controller
@RequestMapping("view")
public class ViewController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private LogItemDao logItemDao;

    //add a category, show list of categories
    @RequestMapping(value="", method = RequestMethod.GET)
    public String doneList(Model model) {
        model.addAttribute("title", "done list");
        model.addAttribute(new LogItem());
        model.addAttribute("logItems", logItemDao.findAll());
        return "index/done-list";
    }


}