package com.wt.test.thor.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @since 2023/3/1
 */
@Data
public class BaseRelation implements Serializable {
    @Serial
    private static final long serialVersionUID = -8553546210008259934L;
    
    private Long id;
    
    private String type;
    
    private Long startNode;
    
    private Long endNode;
    
}
