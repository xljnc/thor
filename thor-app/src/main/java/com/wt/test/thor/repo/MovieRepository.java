package com.wt.test.thor.repo;

import com.wt.test.thor.entity.MovieEntity;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author qiyu
 * @date 2023/2/23
 */
@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Long> {

    MovieEntity getByTitle(String title);


    @Query("match (p:Person {name: $personName})-[r:ACTED_IN]->(m:Movie) return m")
    MovieEntity getByActedPersonName(@Param("personName") String personName);
}
