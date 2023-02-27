package com.wt.test.thor.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 不要使用如下形式来维护人和电影的双向关系，会导致StackOverFlow
 *
 *     @Relationship(type = "ACTED_IN", direction = Relationship.Direction.OUTGOING)
 *     private List<MovieEntity> actedMovies;
 *
 *     @Relationship(type = "DIRECTED", direction = Relationship.Direction.OUTGOING)
 *     private List<MovieEntity> directedMovies;
 *
 * @author qiyu
 * @since 2023/2/22
 */
@Node("Person")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String birthday;

}
