package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonEntity extends BaseNode{

    private String name;

    private String birthday;

}
