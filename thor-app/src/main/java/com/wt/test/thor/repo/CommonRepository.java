package com.wt.test.thor.repo;

import com.wt.test.thor.entity.PathEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.types.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * @author qiyu
 * @since 2023/2/25
 */
@Repository
@RequiredArgsConstructor
public class CommonRepository {
    
    private final Neo4jTemplate neo4jTemplate;
    
    private final Neo4jClient neo4jClient;
    
    private final CommonUtil commonUtil;
    
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
    
    public <T> Page<T> pageByCondition(String cql, Map<String, Object> params, PageRequest pageRequest, Class<T> domainType) {
        if (pageRequest == null || pageRequest.getPageNumber() < 1)
            throw new InvalidParameterException("分页参数异常");
        String countQL = "call { %s } return count(*)";
        long count = neo4jTemplate.count(String.format(countQL, cql), params);
        if (count == 0)
            return new PageImpl<>(Collections.EMPTY_LIST);
        String pageQL;
        if (pageRequest.getPageNumber() == 1)
            pageQL = " limit " + pageRequest.getPageSize();
        else {
            int skip = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
            pageQL = String.format(" skip %d limit %d", skip, pageRequest.getPageSize());
        }
        List<T> data = neo4jTemplate.findAll(cql + pageQL, params, domainType);
        return new PageImpl<>(data, pageRequest, count);
    }
    
    public List<PathEntity> findShortestPath(Long subId, Long subedId) {
        String cql = "match p=shortestPath((p1)-[*..6]-(p2)) where id(p1) = $subId and id(p2) = $subedId "
                + "return p as path";
        Map<String, Object> params = new HashMap<>(4);
        params.put("subId", subId);
        params.put("subedId", subedId);
        return neo4jClient.query(cql).bindAll(params).fetchAs(List.class)
                .mappedBy((typeSystem, record) -> {
                            Path path = record.get("path").asPath();
                            List<PathEntity> pathEntityList = new ArrayList<>();
                            path.forEach(segment -> {
                                        PathEntity pathEntity = PathEntity.builder()
                                                .start(commonUtil.toBaseNode(segment.start()))
                                                .relation(commonUtil.toBaseRelation(segment.relationship()))
                                                .end(commonUtil.toBaseNode(segment.end()))
                                                .build();
                                        pathEntityList.add(pathEntity);
                                    }
                            );
                            return pathEntityList;
                        }
                ).one().orElse(Collections.emptyList());
    }
}
