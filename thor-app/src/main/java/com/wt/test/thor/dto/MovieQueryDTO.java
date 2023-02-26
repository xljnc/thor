package com.wt.test.thor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * @author qiyu
 * @date 2023/2/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieQueryDTO extends PageQueryDTO {
    
    @Serial
    private static final long serialVersionUID = -4755004199932260126L;
    
    @NotBlank
    private String movieTitle;
}
