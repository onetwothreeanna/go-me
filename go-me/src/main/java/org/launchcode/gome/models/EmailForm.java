package org.launchcode.gome.models;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by AnnaYoungyeun on 7/25/17.
 */
public class EmailForm {
    //fields

    @Email
    @NotEmpty
    private String toEmail;

    @NotNull
    @Size(min=1, max=30, message = "Please enter a sender name or e-mail address.")
    private String sender;

    @NotNull
    @Size(min=1, message = "Please enter a message.")
    private String message;

    //constructors

    public EmailForm() {   }

    public EmailForm(String toEmail, String sender, String message) {
        this.toEmail = toEmail;
        this.sender = sender;
        this.message = message;
    }

    //getters and setters

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
