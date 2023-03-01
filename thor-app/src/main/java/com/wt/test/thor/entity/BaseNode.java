package com.wt.test.thor.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @since 2023/3/1
 */
@Data
public class BaseNode implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -6829797576852098672L;
    
    @Id
    @GeneratedValue
    private Long id;
}
