package com.wt.test.thor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;

/**
 * @author qiyu
 * @since 2023/2/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MovieQueryDTO extends PageQueryDTO {
    
    @Serial
    private static final long serialVersionUID = -4755004199932260126L;
    
    @NotBlank
    private String movieTitle;
}
