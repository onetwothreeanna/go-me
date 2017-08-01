package org.launchcode.gome.models;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Created by AnnaYoungyeun on 7/25/17.
 */
public class EmailForm {

    @Email
    @NotEmpty
    private String to;

    @Email
    @NotEmpty
    private String from;

    @NotNull
    @Size(max = 300)
    private String message;

    public EmailForm(String to, String from, String message) {
        this.to = to;
        this.from = from;
        this.message = message;
    }

    public EmailForm(){   }

    public String getTo() {   return to;   }

    public void setTo(String to) {   this.to = to;   }

    public String getMessage() {   return message;   }

    public void setMessage(String message) {   this.message = message;   }


    public String getFrom() {   return from;   }

    public void setFrom(String from) {   this.from = from;   }
}
