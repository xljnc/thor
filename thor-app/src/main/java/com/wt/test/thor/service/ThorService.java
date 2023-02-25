package com.wt.test.thor.service;

import com.wt.test.thor.dto.RelationCreateDTO;
import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.entity.Role;
import com.wt.test.thor.repo.MovieRepository;
import com.wt.test.thor.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qiyu
 * @date 2023/2/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ThorService {

    private final MovieRepository movieRepository;

    private final PersonRepository personRepository;

    public Long createMovie(MovieEntity movieEntity) {
        movieRepository.save(movieEntity);
        return movieEntity.getId();
    }

    public Long createPerson(PersonEntity personEntity) {
        personRepository.save(personEntity);
        return personEntity.getId();
    }

    public MovieEntity getMovieByTitle(String title) {
        return movieRepository.getByTitle(title);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createMemberRelation(RelationCreateDTO createDTO) {
        MovieEntity movieEntity = movieRepository.getByTitle(createDTO.getMovieTitle());
        PersonEntity personEntity = personRepository.getByName(createDTO.getPersonName());
        switch (createDTO.getRelationType()) {
            case 1:
                Role actRole = Role.builder().name("陈真").personEntity(personEntity).build();
                movieEntity.getActors().add(actRole);
                movieRepository.save(movieEntity);
                break;
            case 2:
                movieEntity.getDirectors().add(personEntity);
                movieRepository.save(movieEntity);
                break;
            default:
                log.error("关系类型异常,参数:{}", createDTO);
        }
    }

    public MovieEntity getActedMovie(String personName) {
        return movieRepository.getByActedPersonName(personName);
    }
}
