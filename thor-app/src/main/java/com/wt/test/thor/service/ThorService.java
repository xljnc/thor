package com.wt.test.thor.service;

import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.repo.MovieRepository;
import com.wt.test.thor.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author qiyu
 * @date 2023/2/24
 */
@Service
@RequiredArgsConstructor
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
}
