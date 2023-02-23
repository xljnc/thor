package com.wt.test.thor.bo;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.List;

/**
 * @author qiyu
 * @date 2023/2/21
 */
@Node("Movie")
@Data
public class MovieEntity {

    @Id
//    @GeneratedValue
    private Long id;

    private String title;

    @Property("tagline")
    private String description;

    @Relationship(type = "ACTED_IN", direction = Direction.INCOMING)
    private List<PersonEntity> actors;

    @Relationship(type = "DIRECTED", direction = Direction.INCOMING)
    private List<PersonEntity> directors;
}
