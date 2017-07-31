package org.launchcode.gome.models.data;

import org.launchcode.gome.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */

@Repository
@Transactional
public interface CategoryDao extends CrudRepository<Category, Integer> {

    List<Category> findByUserId(int userId);

}
