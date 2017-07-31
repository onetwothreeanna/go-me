package org.launchcode.gome.models.data;

import org.launchcode.gome.models.LogItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */
@Repository
@Transactional
public interface LogItemDao extends CrudRepository<LogItem, Integer>{
    List<LogItem> findByUserId(int userId);

}
