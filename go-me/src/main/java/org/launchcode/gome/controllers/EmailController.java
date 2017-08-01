package org.launchcode.gome.controllers;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
        model.addAttribute("title", "go-me");
        model.addAttribute("email", new EmailForm());
        return "index/email";
    }

    @RequestMapping(value="email", method = RequestMethod.POST)
    public String sendEmail(Model model, @ModelAttribute @Valid EmailForm email, Errors errors,
                            HttpServletRequest request) throws Exception {
        //check for errors
        if(errors.hasErrors()){
            model.addAttribute("title", "go-me");
            return "index/email";
        }

        if(email.getTo() == null || email.getFrom() == null || email.getMessage() == null){
            model.addAttribute("title", "go-me");
            model.addAttribute("emailError", "Please fill out all fields.");
            model.addAttribute(email);
            return "index/email";
        }


        //set up email
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //find list of logItems for current user
        List<LogItem> logItems = logItemDao.findByUserId(userDao.findByUsername(request.getSession().
                                    getAttribute("currentUser").toString()).getId());

        //set up message
        String formattedString = logItems.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();           //remove trailing spaces from partially initialized arrays
        helper.setTo(email.getTo());
        helper.setText(email.getFrom() + " wants to share a list of their accomplishments with you! \n\n\n" + email.getMessage() + " \n\n " +
                logItems.size() + "  tasks accomplished. \n\n\n" +
                "Check out all you've gotten done: \n " + formattedString + "\n\n\nYou're so cool.");
        helper.setSubject("Hooray productivity!");

        //send message
        sender.send(message);

        //load sent confirmation page
        model.addAttribute("title", "goMe");
        model.addAttribute("emailSent", "Email sent to " + email.getTo());

        return "index/email-sent";
    }

}