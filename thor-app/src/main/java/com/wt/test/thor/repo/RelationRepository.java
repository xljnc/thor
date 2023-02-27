package com.wt.test.thor.repo;

import com.wt.test.thor.constant.RelationTypeEnum;
import com.wt.test.thor.dto.MovieRelationDTO;
import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.entity.RelationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiyu
 * @since 2023/2/27
 */
@Repository
@RequiredArgsConstructor
public class RelationRepository {
    
    private final Neo4jClient neo4jClient;
    
    public List<MovieRelationDTO> getRelationByMovieTitle(String movieTitle, Integer relationType) {
        String cql = "match (p:Person)-[r:ACTED_IN]->(m:Movie {title: $movieTitle}) return p as actor,r as relation, m as movie";
        Map<String, Object> params = new HashMap<>(4);
        params.put("movieTitle", movieTitle);
        params.put("relationType", RelationTypeEnum.getByType(relationType).getName());
        return neo4jClient.query(cql)
                .bindAll(params)
                .fetchAs(MovieRelationDTO.class)
                .mappedBy((typeSystem, record) ->
                        MovieRelationDTO.builder()
                                .person((PersonEntity)record.get("actor").asObject())
                                .relation((RelationEntity)record.get("relation").asObject())
                                .movie((MovieEntity)record.get("movie").asObject())
                                .build()
                ).all().stream().toList();
    }
}
