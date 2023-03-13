package com.wt.test.thor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @since 2023/2/24
 */
@Data
public class RelationCreateDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 916714382889328394L;
    
    @NotNull
    private Long subId;
    
    @NotNull
    private Long subedId;
    
    @NotBlank
    private String subType;
}
