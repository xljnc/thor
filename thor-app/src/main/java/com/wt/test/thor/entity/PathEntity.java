package com.wt.test.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @since 2023/3/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PathEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -814672874013335410L;
    
    private BaseNode start;
    
    private BaseRelation relation;
    
    private BaseNode end;
}
