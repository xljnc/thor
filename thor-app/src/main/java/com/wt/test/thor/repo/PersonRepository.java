package com.wt.test.thor.repo;

import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author qiyu
 * @date 2023/2/23
 */
@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    PersonEntity getByName(String name);

}
