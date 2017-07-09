package org.launchcode.gome.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by AnnaYoungyeun on 7/3/17.
 */
@Entity
public class Category {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<LogItem> logItems = new ArrayList<>();

    @ManyToOne
    private User user;

    //constructors
    public Category(String name) {
        this.name = name;
    }

    public Category(){  }

    //getters + setters

    public int getId() {  return id;  }

    public void setId(int id) {  this.id = id;  }

    public String getName() {  return name;  }

    public void setName(String name) {  this.name = name;  }

    public List<LogItem> getLogItems() {  return logItems;  }

    public void setLogItems(List<LogItem> logItems) {  this.logItems = logItems;  }


}
