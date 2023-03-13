package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @author qiyu
 * @since 2023/3/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseRelation implements Serializable {
    @Serial
    private static final long serialVersionUID = -8553546210008259934L;
    
    private Long id;
    
    private String type;
    
    private Long startNode;
    
    private Long endNode;
    
    private Map<String,Object> properties;
}
