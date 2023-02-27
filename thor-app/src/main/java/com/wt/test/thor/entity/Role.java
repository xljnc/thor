package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @author qiyu
 * @since 2023/2/25
 */
@RelationshipProperties
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @RelationshipId
    @GeneratedValue
    private Long id;

    @TargetNode
    private PersonEntity personEntity;

    private String name;
}
