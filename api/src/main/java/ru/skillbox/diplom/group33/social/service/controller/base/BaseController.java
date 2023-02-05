package ru.skillbox.diplom.group33.social.service.controller.base;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

public interface BaseController<Dto extends BaseDto, SearchDto extends BaseSearchDto> {

    @GetMapping(value = "/{id}")
    ResponseEntity<Dto> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<Dto> getAll(SearchDto searchDto, Pageable page);

    @PostMapping
    ResponseEntity<Dto> create(@RequestBody Dto dto);

    @PutMapping
    ResponseEntity<Dto> update(@RequestBody Dto dto);

    @DeleteMapping(value = "/{id}")
    ResponseEntity deleteById(@PathVariable Long id);
}