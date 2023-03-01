package com.wt.test.thor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.List;

/**
 * @author qiyu
 * @since 2023/2/21
 */
@Node("Movie")
@Data
public class MovieEntity extends BaseNode{

    private String title;

    @Property("tagline")
    @JsonProperty("tagline")
    private String description;

    @Relationship(type = "ACTED_IN", direction = Direction.INCOMING)
    private List<Role> actors;

    @Relationship(type = "DIRECTED", direction = Direction.INCOMING)
    private List<PersonEntity> directors;
}
