package com.wt.test.thor.util;

import com.wt.test.thor.entity.BaseNode;
import com.wt.test.thor.entity.BaseRelation;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiyu
 * @since 2023/3/1
 */
@Component
@RequiredArgsConstructor
public class CommonUtil {
    
    private final JsonUtil jsonUtil;
    
    public <T extends BaseNode> T nodeToEntity(Node node, Class<T> clazz) {
        T entity = jsonUtil.readValue(node.asMap(), clazz);
        entity.setId(node.id());
        return entity;
    }
    
    public <T extends BaseRelation> T relationToEntity(Relationship relationship, Class<T> clazz) {
        T entity = jsonUtil.readValue(relationship.asMap(), clazz);
        entity.setId(relationship.id());
        entity.setType(relationship.type());
        entity.setStartNode(relationship.startNodeId());
        entity.setEndNode(relationship.endNodeId());
        return entity;
    }
    
    public BaseRelation toBaseRelation(Relationship relationship) {
        return BaseRelation.builder().id(relationship.id()).type(relationship.type())
                .startNode(relationship.startNodeId()).endNode(relationship.endNodeId())
                .properties(relationship.asMap()).build();
    }
    
    public BaseNode toBaseNode(Node node) {
        List<String> labelList = new ArrayList<>();
        node.labels().forEach(label -> labelList.add(label));
        return BaseNode.builder().properties(node.asMap()).labels(labelList).id(node.id()).build();
    }
}
