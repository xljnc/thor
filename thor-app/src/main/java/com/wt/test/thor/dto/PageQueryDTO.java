package com.wt.test.thor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @since 2023/2/25
 */
@Data
public class PageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -299771388512649660L;
    
    @NotNull
    private Integer page;
    
    @NotNull
    private Integer size;
    
}
