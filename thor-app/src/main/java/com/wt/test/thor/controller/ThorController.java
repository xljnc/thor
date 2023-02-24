package com.wt.test.thor.controller;

import com.wt.test.thor.entity.MovieEntity;
import com.wt.test.thor.entity.PersonEntity;
import com.wt.test.thor.service.ThorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiyu
 * @date 2023/2/24
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
    public MovieEntity createPerson(@RequestParam("title")String title) {
        return thorService.getMovieByTitle(title);
    }
}
