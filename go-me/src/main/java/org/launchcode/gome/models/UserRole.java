//package org.launchcode.gome.models;
//
//import org.hibernate.validator.constraints.Email;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//
//
///**
// * Created by AnnaYoungyeun on 7/15/17.
// */
//
//@Entity
//public class UserRole {
//    //fields
//    @Id
//    @GeneratedValue
//    private int id;
//
//    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "username")
//    private User user;
//
//    @NotNull
//    private String role;
//
//    //constructors
//    public UserRole(){   }
//
//    public UserRole(User user, String role) {
//        this.user = user;
//        this.role = role;
//    }
//
//    //getters + setters
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//}
//
