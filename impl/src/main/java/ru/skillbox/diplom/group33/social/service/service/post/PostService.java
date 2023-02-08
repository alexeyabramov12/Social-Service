package ru.skillbox.diplom.group33.social.service.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.mapper.post.PostMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    private final PostMapper mapper;


    public PostDto getById(Long id) {
        Post post = repository.findById(id).orElseThrow(RuntimeException::new);
        return mapper.toPostDto(post);
    }

    public Page<PostDto> getAll(PostSearchDto searchDto, Pageable page) {
        return null;
    }

    public PostDto create(PostDto dto) {
        Post post = repository.save(mapper.toPost(dto));
        return mapper.toPostDto(post);
    }

    public PostDto update(PostDto dto) {
        Post post = repository.save(mapper.toPost(dto));
        return mapper.toPostDto(post);
    }

    public void deleteById(Long id) {
        Post post = repository.findById(id).orElseThrow(RuntimeException::new);
        post.setIsDeleted(true);
    }

}
