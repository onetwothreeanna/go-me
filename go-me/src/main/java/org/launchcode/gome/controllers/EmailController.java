package org.launchcode.gome.controllers;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.EmailForm;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.launchcode.gome.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/*Starter code from: http://www.opencodez.com/java/java-mail-framework-using-spring-boot.htm */

@Controller
@RequestMapping("go-me")

public class EmailController {

    @Autowired
    private JavaMailSender sender;
    @Autowired
    private LogItemDao logItemDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;

    //TO DO Set up email form and page with ability to change TO and FROM.
    @RequestMapping(value = "email", method = RequestMethod.GET)
    public String loadEmailForm(Model model) {
        model.addAttribute("email", new EmailForm());
        model.addAttribute("title", "go-me");
        return "index/email";
    }

    @RequestMapping(value="email", method = RequestMethod.POST)
    public String sendEmail(@ModelAttribute @Valid EmailForm email, Errors errors,
                            Model model, HttpServletRequest request) throws Exception {

        if(errors.hasErrors()){
            model.addAttribute("title", "goMe");
            return "index/email";
        }

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        List<String> logItems = new ArrayList<>();
        for(Category category: categoryDao.findByUserId(userDao.findByUsername(request.getSession().getAttribute("currentUser").toString()).getId())){
            for(LogItem logItem : category.getLogItems()) {
                logItems.add(logItem.getDescription());
            }
        }

        String formattedString = logItemDao.findAll().toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();           //remove trailing spaces from partially initialized arrays
        helper.setTo(email.getTo());
        helper.setFrom(email.getTo());
        helper.setText(email.getFrom() + " wants to share a list of their accomplishments with you!" + "\n\n\n" + email.getMessage() + " \n\n " + logItems.size() + " tasks." + " \n\n " +
                "Check out all you've gotten done: \n " + formattedString + "\n\n\nYou're so cool.");
        helper.setSubject("Hooray productivity!");

        sender.send(message);

        model.addAttribute("title", "goMe");
        model.addAttribute("emailSent", "Email sent to " + email.getTo());

        model.addAttribute(new LogItem());
        model.addAttribute("logItems", logItemDao.findByUserId(userDao.findByUsername(request.getSession()
                .getAttribute("currentUser").toString()).getId()));
        return "index/email-sent";
    }

}