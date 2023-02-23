package com.wt.test.thor.repo;

import com.wt.test.thor.bo.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author qiyu
 * @date 2023/2/23
 */
@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
