package org.launchcode.gome.controllers;

import javax.mail.internet.MimeMessage;

import org.launchcode.gome.models.Category;
import org.launchcode.gome.models.LogItem;
import org.launchcode.gome.models.data.CategoryDao;
import org.launchcode.gome.models.data.LogItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*Starter code from: http://www.opencodez.com/java/java-mail-framework-using-spring-boot.htm */

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender sender;
    @Autowired
    private LogItemDao logItemDao;
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping("/email")
    @ResponseBody
    String home() {
        try {
            sendEmail();
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: "+ex;
        }
    }

    private void sendEmail() throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        List<String> logItems = new ArrayList<>();
        for(Category category: categoryDao.findAll()){
            for(LogItem logItem : category.getLogItems()) {
                logItems.add(logItem.getDescription());
            }
        }
        helper.setTo("plentybyanna@gmail.com");
        helper.setText("You've accomplished " + logItems.size() + " tasks!");
        helper.setSubject("You did things.");

        sender.send(message);
    }
}