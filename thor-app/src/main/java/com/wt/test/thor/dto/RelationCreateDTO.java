package com.wt.test.thor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author qiyu
 * @date 2023/2/24
 */
@Data
public class RelationCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 916714382889328394L;

    @NotBlank
    private String personName;

    @NotBlank
    private String movieTitle;

    @NotNull
    private Integer relationType;
}
