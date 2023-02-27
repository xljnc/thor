package com.wt.test.thor.dto;

import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.entity.RelationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class MovieRelationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2881933571619050743L;
    
    private MovieEntity movie;
    
    private PersonEntity person;
    
    private RelationEntity relation;
}
