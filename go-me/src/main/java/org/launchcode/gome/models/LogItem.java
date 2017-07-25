package org.launchcode.gome.models;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * Created by AnnaYoungyeun on 7/3/17.
 */

@Entity
public class LogItem {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=300)
    private String description;

    private String dateTime;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    //constructors
    public LogItem(String description) {
        this.description = description;
    }

    public LogItem(){ }

    //getters & setters
    public int getId() {  return id;  }

    public void setId(int id) {  this.id = id;  }

    public String getDescription() {  return description;  }

    public void setDescription(String description) {  this.description = description;  }

    public Category getCategory() {  return category;  }

    public void setCategory(Category category) {  this.category = category;  }

    public User getUser() {  return user;  }

    public void setUser(User user) {  this.user = user;   }

    public String getDateTime() {   return dateTime;   }

    public void setDateTime(String dateTime) {   this.dateTime = dateTime;   }

    @Override
    public String toString() {

        return "\n --" + dateTime + ":   " + description;
    }
}
