package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author qiyu
 * @since 2023/3/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseNode implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -6829797576852098672L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    private Map<String,Object> properties;
    
    private List<String> labels;
}
