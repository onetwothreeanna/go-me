package org.launchcode.gome.models.data;

import org.launchcode.gome.models.LogItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by AnnaYoungyeun on 7/3/17.
 */
@Repository
@Transactional
public interface LogItemDao extends CrudRepository<LogItem, Integer>{
    LogItem findByUserId(int userId);
}
