package ru.skillbox.diplom.group33.social.service.controller.post;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.controller.base.BaseController;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@RequestMapping(PathConstant.URL + "post/")
public interface PostController extends BaseController<PostDto, PostSearchDto> {

    @Override
    @GetMapping("{id}")
    ResponseEntity<PostDto> getById(@PathVariable Long id);

    @Override
    @GetMapping()
    ResponseEntity<PostDto> getAll(PostSearchDto searchDto, Pageable page);

    @Override
    @PostMapping()
    ResponseEntity<PostDto> create(@RequestBody PostDto dto);

    @Override
    @PutMapping()
    ResponseEntity<PostDto> update(@RequestBody PostDto dto);

    @Override
    @DeleteMapping(value = "{id}")
    ResponseEntity deleteById(@PathVariable Long id);

}
