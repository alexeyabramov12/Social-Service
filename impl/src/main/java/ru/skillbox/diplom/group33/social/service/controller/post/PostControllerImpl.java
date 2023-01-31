package ru.skillbox.diplom.group33.social.service.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.service.post.PostService;

@RequiredArgsConstructor
@RestController
public class PostControllerImpl implements PostController {

    private final PostService postService;


    @Override
    public ResponseEntity<PostDto> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getById(id));
    }

    @Override
    public ResponseEntity<PostDto> getAll(PostSearchDto searchDto, Pageable page) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll(searchDto, page));
    }

    @Override
    public ResponseEntity<PostDto> create(PostDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.create(dto));
    }

    @Override
    public ResponseEntity<PostDto> update(PostDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(dto));
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}