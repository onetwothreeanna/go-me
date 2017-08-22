package org.launchcode.gome.controllers;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.User;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.launchcode.gome.models.data.UserDao;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String addItem(Model model, HttpServletRequest request) {
        //get current user's logItems
        List<LogItem> logItems = logItemDao.findByUserIdOrderByIdDesc(userDao.findByUsername(request.getSession()
                .getAttribute("currentUser").toString()).getId());
        List<LogItem> todayItems = new ArrayList<>();

        //Format today's date to match logItem dates stored as strings in DB
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E - MM/dd/yyyy");
        DateTimeFormatter writtenOutFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        String CurrentDate = now.format(formatter);
        String CurrentDateWrittenOut = now.format(writtenOutFormatter);

        //for each of this user's logItems, if the date is Today's date, pass to view.
        for(LogItem logitem: logItems){
            String logItemDate = logitem.getDateTime().substring(0,16);
            if(logItemDate.equals(CurrentDate)){
                todayItems.add(logitem);
            } else {
                break;
            }
        }
        model.addAttribute("title", "goMe");
        model.addAttribute(new LogItem());
        model.addAttribute("categories", categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));
        model.addAttribute("todayItems", todayItems);
        model.addAttribute("todayDate", CurrentDateWrittenOut);
        return "index/add-item";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid LogItem logItem, Errors errors,
                          @RequestParam int categoryId, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            model.addAttribute(logItem);
            model.addAttribute("categories", categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));

            return "index/add-item";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E - MM/dd/yyyy - hh:mm a");
        String dateTime = now.format(formatter);
        logItem.setDateTime(dateTime);

        Category category = categoryDao.findOne(categoryId);
        User user = category.getUser();
        logItem.setUser(user);
        logItem.setCategory(category);
        logItemDao.save(logItem);

        return "redirect:/go-me";
    }


    //display donelist
    @RequestMapping(value="done-list", method = RequestMethod.GET)
    public String doneList(Model model, HttpServletRequest request) {
        List<LogItem> logItems = logItemDao.findByUserIdOrderByIdDesc(userDao.findByUsername(request.getSession()
                .getAttribute("currentUser").toString()).getId());

        model.addAttribute("title", "goMe");
        model.addAttribute(new LogItem());
        model.addAttribute("logItems", logItems);
        return "index/done-list";
    }

    //remove logged items
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String removeLoggedItems(Model model, HttpServletRequest request) {
        model.addAttribute("logItems", logItemDao.findByUserIdOrderByIdDesc(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId()));
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
