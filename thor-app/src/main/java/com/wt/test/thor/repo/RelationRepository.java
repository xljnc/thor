package com.wt.test.thor.repo;

import com.wt.test.thor.constant.RelationTypeEnum;
import com.wt.test.thor.dto.MovieRelationDTO;
import com.wt.test.thor.dto.RelationCreateDTO;
import com.wt.test.thor.dto.RelationQueryDTO;
import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.entity.RelationEntity;
import com.wt.test.thor.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author qiyu
 * @since 2023/2/27
 */
@Repository
@RequiredArgsConstructor
public class RelationRepository {
    
    private final Neo4jClient neo4jClient;
    
    private final CommonUtil commonUtil;
    
    public List<MovieRelationDTO> getRelationByMovieTitle(String movieTitle, Integer relationType) {
        String cql = "match (p:Person)-[r:ACTED_IN]->(m:Movie {title: $movieTitle}) return p as person,r as relation, m as movie";
        Map<String, Object> params = new HashMap<>(4);
        params.put("movieTitle", movieTitle);
        params.put("relationType", RelationTypeEnum.getByType(relationType).getName());
        return neo4jClient.query(cql).bindAll(params).fetchAs(MovieRelationDTO.class).mappedBy((typeSystem, record) -> MovieRelationDTO.builder().person(commonUtil.nodeToEntity(record.get("person").asNode(), PersonEntity.class)).relation(commonUtil.relationToEntity(record.get("relation").asRelationship(), RelationEntity.class)).movie(commonUtil.nodeToEntity(record.get("movie").asNode(), MovieEntity.class)).build()).all().stream().toList();
    }
    
    public Long createRelation(RelationCreateDTO createDTO) {
        String cql = "match (p1),(p2) " + "where id(p1) = $subId and id(p2) = $subedId " + "merge (p1)-[r:%s]->(p2) " + "return id(r)";
        cql = String.format(cql, createDTO.getSubType());
        Map<String, Object> params = new HashMap<>(4);
        params.put("subId", createDTO.getSubId());
        params.put("subedId", createDTO.getSubedId());
        return neo4jClient.query(cql).bindAll(params).fetchAs(Long.class).one().orElse(null);
    }
    
    public boolean hasBidirectionalRelation(RelationQueryDTO queryDTO) {
        String cql = "match (p1)-[r:%s]-(p2) where id(p1) = $subId and id(p2) = $subedId return count(distinct(r))";
        cql = String.format(cql, queryDTO.getSubType());
        Map<String, Object> params = new HashMap<>(4);
        params.put("subId", queryDTO.getSubId());
        params.put("subedId", queryDTO.getSubedId());
        return neo4jClient.query(cql).bindAll(params).fetchAs(Long.class).one().map(count -> Objects.equals(count, 2L)).orElse(false);
    }
}
