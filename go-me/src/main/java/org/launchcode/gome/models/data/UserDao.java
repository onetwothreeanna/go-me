package org.launchcode.gome.models.data;

import org.launchcode.gome.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */
@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

//    @Query("SELECT user FROM users WHERE username = ?")
//    public User findByUsername (User user);

    User findByUsername(String username); //CRUD bookmark
}
