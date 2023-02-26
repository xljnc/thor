package com.wt.test.thor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @date 2023/2/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -299771388512649660L;
    
    @NotNull
    private Integer page;
    
    @NotNull
    private Integer size;
    
}
