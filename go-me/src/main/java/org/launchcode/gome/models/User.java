//package org.launchcode.gome.models;
//
//import org.hibernate.validator.constraints.Email;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
///**
// * Created by AnnaYoungyeun on 7/3/17.
// */
//@Entity
//public class User {
//
//    //fields
//    @NotNull
//    @Size(min=5, max=15)
//    private String username;
//
//    @Email
//    private String email;
//
//    @NotNull
//    @Size(min=6)
//    private String password;
//
//    @NotNull
//    private String verify;
//
//    //constructors
//    public User(String username, String email, String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
//
//    public User(){  }
//
//    //getters + setters
//
//    public String getUsername() {  return username;  }
//
//    public void setUsername(String username) {  this.username = username;  }
//
//    public String getEmail() {  return email;  }
//
//    public void setEmail(String email) {  this.email = email;  }
//
//    public String getPassword() {  return password;  }
//
//    public void setPassword(String password) {  this.password = password;  }
//
//    public String getVerify() {  return verify;  }
//
//    public void setVerify(String verify) {  this.verify = verify;  }
//
//
//}
