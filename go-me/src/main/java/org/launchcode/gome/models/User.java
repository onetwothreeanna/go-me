package org.launchcode.gome.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */
@Entity
public class User {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=4, max=15)
    private String username;

    @Email
    private String email;

    @NotNull
    @Size(min=6)
    private String password;

    @NotNull
    private String verify;

    private boolean enabled;

    private List<UserRole> userRole = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<LogItem> logItems = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Category> categories = new ArrayList<>();

    //constructors
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(){  }

    //getters + setters

    public String getUsername() {  return username;  }

    public void setUsername(String username) {  this.username = username;  }

    public String getEmail() {  return email;  }

    public void setEmail(String email) {  this.email = email;  }

    public String getPassword() {  return password;  }

    public void setPassword(String password) {  this.password = password;  }

    public String getVerify() {  return verify;  }

    public void setVerify(String verify) {  this.verify = verify;  }

    public int getId() {  return id;  }

    public void setId(int id) {  this.id = id;  }

    public List<LogItem> getLogItems() {  return logItems;  }

    public void setLogItems(List<LogItem> logItems) {  this.logItems = logItems;  }

    public List<Category> getCategories() {  return categories;  }

    public void setCategories(List<Category> categories) {  this.categories = categories;  }

    public boolean isEnabled() {   return enabled;   }

    public void setEnabled(boolean enabled) {   this.enabled = enabled;   }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }
}
