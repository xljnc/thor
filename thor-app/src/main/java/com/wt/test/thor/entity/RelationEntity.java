package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * @author qiyu
 * @since 2023/2/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationEntity extends BaseRelation {
    @Serial
    private static final long serialVersionUID = 6471509219286210548L;
    
    private String name;
    
}
