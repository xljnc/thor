package com.wt.test.thor.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * @author qiyu
 * @date 2023/2/22
 */
@Node("Person")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String birthday;


    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.OUTGOING)
    private List<MovieEntity> actedMovies;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.OUTGOING)
    private List<MovieEntity> directedMovies;
}
