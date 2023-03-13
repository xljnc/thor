package com.wt.test.thor.service;

import com.wt.test.thor.dto.MovieQueryDTO;
import com.wt.test.thor.dto.MovieRelationCreateDTO;
import com.wt.test.thor.dto.MovieRelationDTO;
import com.wt.test.thor.dto.RelationCreateDTO;
import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.entity.Role;
import com.wt.test.thor.repo.CommonRepository;
import com.wt.test.thor.repo.MovieRepository;
import com.wt.test.thor.repo.PersonRepository;
import com.wt.test.thor.repo.RelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiyu
 * @since 2023/2/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ThorService {
    
    private final MovieRepository movieRepository;
    
    private final PersonRepository personRepository;
    
    private final CommonRepository commonRepository;
    
    private final RelationRepository relationRepository;
    
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
    public void createMovieRelation(MovieRelationCreateDTO createDTO) {
        MovieEntity movieEntity = movieRepository.getByTitle(createDTO.getMovieTitle());
        PersonEntity personEntity = personRepository.getByName(createDTO.getPersonName());
        switch (createDTO.getRelationType()) {
            case 1:
                Role actRole = Role.builder().name(createDTO.getRelationName()).personEntity(personEntity).build();
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
    
    public List<MovieEntity> getActedMovie(String personName) {
        return movieRepository.getByActedPersonName(personName);
    }
    
    public List<PersonEntity> getActorByMovieTitle(String movieTitle) {
        return commonRepository.getActorByMovieTitle(movieTitle);
    }
    
    public List<PersonEntity> getDirectorByMovieTitle(String movieTitle) {
        String cql = "match (p:Person)-[r:DIRECTED]->(m:Movie {title: $movieTitle}) return p";
        Map<String, Object> params = new HashMap<>(4);
        params.put("movieTitle", movieTitle);
        return commonRepository.findAllByCondition(cql, params, PersonEntity.class);
    }
    
    public Page<PersonEntity> pageActorByMovieTitle(MovieQueryDTO queryDTO) {
        String cql = "match (p:Person)-[r:ACTED_IN]->(m:Movie {title: $movieTitle}) return p";
        Map<String, Object> params = new HashMap<>(4);
        params.put("movieTitle", queryDTO.getMovieTitle());
        PageRequest pageRequest = PageRequest.of(queryDTO.getPage(), queryDTO.getSize());
        return commonRepository.pageByCondition(cql, params, pageRequest, PersonEntity.class);
    }
    
    public List<PersonEntity> findCoActor(String personName) {
        String cql = "match (p:Person {name: $personName})-[r:ACTED_IN]->(m:Movie) <-[:ACTED_IN]-(coActor:Person) return coActor";
        Map<String, Object> params = new HashMap<>(4);
        params.put("personName", personName);
        return commonRepository.findAllByCondition(cql, params, PersonEntity.class);
    }
    
    public List<MovieRelationDTO> getAllMovieRelation(String movieTitle, Integer relationType) {
        return relationRepository.getRelationByMovieTitle(movieTitle, relationType);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Long createMemberRelation(RelationCreateDTO createDTO) {
        return relationRepository.createRelation(createDTO);
    }
}
