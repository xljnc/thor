package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @since 2023/2/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6471509219286210548L;
    
    private String name;
    
    private String type;
}
