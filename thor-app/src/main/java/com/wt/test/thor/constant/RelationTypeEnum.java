package com.wt.test.thor.constant;

import lombok.Getter;

import java.util.Objects;

/**
 * @author qiyu
 * @since 2023/2/27
 */
@Getter
public enum RelationTypeEnum {
    
    ACTED_IN(1, "ACTED_IN"),
    
    DIRECTED(2, "DIRECTED"),
    
    ;
    
    RelationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    
    private final Integer type;
    
    private final String name;
    
    
    public static RelationTypeEnum getByType(Integer type) {
        for (RelationTypeEnum item : RelationTypeEnum.values()) {
            if (Objects.equals(type, item.getType()))
                return item;
        }
        return RelationTypeEnum.ACTED_IN;
    }
}
