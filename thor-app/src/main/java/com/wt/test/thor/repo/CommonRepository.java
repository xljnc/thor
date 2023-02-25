package com.wt.test.thor.repo;

import com.wt.test.thor.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiyu
 * @date 2023/2/25
 */
@Repository
@RequiredArgsConstructor
public class CommonRepository {

    private final Neo4jTemplate neo4jTemplate;

    public List<PersonEntity> getActorByMovieTitle(@Param("movieTitle") String movieTitle) {
        String cql = "match (p:Person)-[r:ACTED_IN]->(m:Movie {title: $movieTitle}) return p";
        Map<String, Object> params = new HashMap<>(4);
        params.put("movieTitle", movieTitle);
        return neo4jTemplate.findAll(cql, params, PersonEntity.class);
    }

    public <T> T findOneByCondition(String cql, Map<String, Object> params, Class<T> domainType) {
        return neo4jTemplate.findOne(cql, params, domainType).orElse(null);
    }

    public <T> List<T> findAllByCondition(String cql, Map<String, Object> params, Class<T> domainType) {
        return neo4jTemplate.findAll(cql, params, domainType);
    }
}
