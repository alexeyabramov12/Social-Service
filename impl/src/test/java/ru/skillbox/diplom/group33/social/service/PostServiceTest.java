package ru.skillbox.diplom.group33.social.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.mapper.post.PostMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;
import ru.skillbox.diplom.group33.social.service.service.PostService;
import ru.skillbox.diplom.group33.social.service.service.like.LikeService;
import ru.skillbox.diplom.group33.social.service.service.tag.TagService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository repository;

    @Mock
    private PostMapper mapper;

    @Mock
    private TagService tagService;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private PostService postService;

    @Test
    public void testGetById() {
        Post post = new Post();
        post.setId(1L);
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(post));
        when(mapper.convertToDto(any(Post.class))).thenReturn(postDto);

        PostDto result = postService.getById(1L);

        Assertions.assertEquals(postDto.getId(), result.getId());
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).convertToDto(post);
    }

    @Test
    public void testGetAll() {
        Post post = new Post();
        post.setId(1L);
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        PostDto postDto = new PostDto();
        postDto.setId(1L);
        List<PostDto> postDtos = new ArrayList<>();
        postDtos.add(postDto);

        Page<Post> page = new PageImpl<>(posts);
        Pageable pageable = PageRequest.of(0, 10);
        PostSearchDto searchDto = new PostSearchDto();
        searchDto.setTags(new HashSet<>());

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(mapper.convertToDto(any(Post.class))).thenReturn(postDto);
        when(likeService.getMyLike(anyLong(), any(LikeType.class))).thenReturn(null);

        Page<PostDto> result = postService.getAll(searchDto, pageable);

        Assertions.assertEquals(1L, result.getContent().get(0).getId());
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(1)).convertToDto(post);
        verify(likeService, times(1)).getMyLike(anyLong(), any(LikeType.class));
    }

    @Test
    public void testCreate() {
        Post post = new Post();
        post.setId(1L);
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        Set<String> tags = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();

        when(mapper.initEntity(any(PostDto.class))).thenReturn(post);
        when(tagService.create(any(Post.class), anySet())).thenReturn(tagSet);
        when(repository.save(any(Post.class))).thenReturn(post);
        when(mapper.convertToDto(any(Post.class))).thenReturn(postDto);

        PostDto result = postService.create(postDto);

        Assertions.assertEquals(1L, result.getId());
        verify(mapper, times(1)).initEntity(postDto);
        verify(tagService, times(1)).create(post, tags);
        verify(repository, times(1)).save(post);
        verify(mapper, times(1)).convertToDto(post);
    }

    @Test
    public void testUpdate() {
        Post post = new Post();
        post.setId(1L);
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        Set<Tag> tagSet = new HashSet<>();

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(post));
        when(tagService.update(any(Post.class), anySet(), anySet())).thenReturn(tagSet);
        when(mapper.updateEntity(any(PostDto.class), any(Post.class))).thenReturn(post);
        when(repository.save(any(Post.class))).thenReturn(post);
        when(mapper.convertToDto(any(Post.class))).thenReturn(postDto);

        PostDto result = postService.update(1L, postDto);

        Assertions.assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
        verify(tagService, times(1)).update(post, post.getTags(), postDto.getTags());
        verify(mapper, times(1)).updateEntity(postDto, post);
        verify(repository, times(1)).save(post);
        verify(mapper, times(1)).convertToDto(post);
    }

    @Test
    public void testDeleteById() {
        postService.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
