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

    User findByUsername(String username); //CRUD bookmark

//    User findByCurrentUserSessionId(String currentUserSessionId);
}
