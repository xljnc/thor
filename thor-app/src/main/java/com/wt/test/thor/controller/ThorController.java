package com.wt.test.thor.controller;

import com.wt.test.thor.dto.*;
import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PathEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.service.ThorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qiyu
 * @since 2023/2/24
 */
@RestController
@RequestMapping("/thor")
@RequiredArgsConstructor
public class ThorController {
    
    private final ThorService thorService;
    
    @PostMapping("/movie/create")
    public Long createMovie(@RequestBody @Valid MovieEntity movieEntity) {
        return thorService.createMovie(movieEntity);
    }
    
    @PostMapping("/person/create")
    public Long createPerson(@RequestBody @Valid PersonEntity personEntity) {
        return thorService.createPerson(personEntity);
    }
    
    @GetMapping("/movie/get_by_title")
    public MovieEntity getMovieByTitle(@RequestParam("title") String title) {
        return thorService.getMovieByTitle(title);
    }
    
    @PostMapping("/movie/relation/create")
    public void createMemberRelation(@RequestBody @Valid MovieRelationCreateDTO createDTO) {
        thorService.createMovieRelation(createDTO);
    }
    
    @GetMapping("/person/get_acted_movie")
    public List<MovieEntity> getActedMovie(@RequestParam("personName") String personName) {
        return thorService.getActedMovie(personName);
    }
    
    @GetMapping("/movie/get_actor")
    public List<PersonEntity> getActorByMovieTitle(@RequestParam("movieTitle") String movieTitle) {
        return thorService.getActorByMovieTitle(movieTitle);
    }
    
    @GetMapping("/movie/get director")
    public List<PersonEntity> getDirectorByMovieTitle(@RequestParam("movieTitle") String movieTitle) {
        return thorService.getDirectorByMovieTitle(movieTitle);
    }
    
    @PostMapping("/movie/page_actor")
    public Page<PersonEntity> getActorByMovieTitle(@RequestBody @Valid MovieQueryDTO queryDTO) {
        return thorService.pageActorByMovieTitle(queryDTO);
    }
    
    @GetMapping("/person/get_co_actor")
    public List<PersonEntity> getCoActors(@RequestParam("personName") String personName) {
        return thorService.findCoActor(personName);
    }
    
    @GetMapping("/movie/get_relation")
    public List<MovieRelationDTO> getAllMovieRelation(@RequestParam("movieTitle") String movieTitle,
                                                      @RequestParam("relationType") Integer relationType) {
        return thorService.getAllMovieRelation(movieTitle, relationType);
    }
    
    @PostMapping("/relation/create")
    public Long createMemberRelation(@RequestBody @Valid RelationCreateDTO createDTO) {
        return thorService.createMemberRelation(createDTO);
    }
    
    @PostMapping("/relation/bidirectional")
    public boolean hasBidirectionalRelation(@RequestBody @Valid RelationQueryDTO queryDTO) {
        return thorService.hasBidirectionalRelation(queryDTO);
    }
    
    @PostMapping("/person/mutual")
    public List<PersonEntity> findMutualPerson(@RequestBody @Valid RelationQueryDTO queryDTO) {
        return thorService.findMutualPerson(queryDTO);
    }
    
    @GetMapping("/path/shortest")
    public List<PathEntity> findShortestPath(@RequestParam("subId") Long subId, @RequestParam("subedId") Long subedId) {
        return thorService.findShortestPath(subId, subedId);
    }
}
