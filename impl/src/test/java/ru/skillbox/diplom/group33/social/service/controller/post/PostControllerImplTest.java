package ru.skillbox.diplom.group33.social.service.controller.post;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import ru.skillbox.diplom.group33.social.service.config.AbstractIntegrationTest;
import ru.skillbox.diplom.group33.social.service.config.RestResponsePage;
import ru.skillbox.diplom.group33.social.service.config.SourceDataFactory;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;
import ru.skillbox.diplom.group33.social.service.repository.post.comment.CommentRepository;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostControllerImplTest extends AbstractIntegrationTest {

    private final PostDto postDto = SourceDataFactory.postDto();
    @Autowired
    private PostRepository repository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Order(1)
    void create() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getTemplate()
                .getRootUri() + "/api/v1/post");
        builder.queryParam("publishDate", ZonedDateTime.now().toEpochSecond());

        ResponseEntity<PostDto> response = getTemplate()
                .postForEntity(builder.build().encode().toUri(),
                        new HttpEntity<>(SourceDataFactory.postDto()), PostDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    void getAll() {
        Map<String, String> params = new HashMap<>();
        params.put("size", "20");

        ResponseEntity<RestResponsePage<PostDto>> response = getTemplate()
                .exchange("/api/v1/post", HttpMethod.GET, new HttpEntity<>(""),
                        ParameterizedTypeReference.forType(TypeUtils.parameterize(
                                RestResponsePage.class, PostDto.class)), params);

        Objects.requireNonNull(response.getBody()).forEach(p -> {
            assertEquals(p.getPostText(), postDto.getPostText());
            assertEquals(p.getTitle(), postDto.getTitle());
        });
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    void getById() {
        Long id = repository.findAll().get(0).getId();
        ResponseEntity<PostDto> response = getTemplate()
                .getForEntity("/api/v1/post/{id}", PostDto.class, id);
        assertEquals(id, Objects.requireNonNull(response.getBody()).getId());
        assertEquals(postDto.getPostText(), Objects.requireNonNull(response.getBody()).getPostText());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    void update() {
        Long id = repository.findAll().get(0).getId();
        postDto.setId(id);
        postDto.setTitle("New title");

        ResponseEntity<PostDto> response = getTemplate()
                .exchange("/api/v1/post/{id}", HttpMethod.PUT,
                        new HttpEntity<>(postDto), PostDto.class, id);

        assertEquals(postDto.getTitle(), Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    void createComment() {
        Long id = repository.findAll().get(0).getId();
        CommentDto commentDto = SourceDataFactory.commentDto(id);

        ResponseEntity<CommentDto> response = getTemplate().postForEntity("/api/v1/post/{id}/comment",
                new HttpEntity<>(commentDto), CommentDto.class, id);

        assertEquals(commentDto.getCommentText(), Objects.requireNonNull(response.getBody()).getCommentText());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(6)
    void getComments() {
        Long id = repository.findAll().get(0).getId();
        CommentDto commentDto = SourceDataFactory.commentDto(id);
        Map<String, String> params = new HashMap<>();
        params.put("size", "20");
        params.put("id", String.valueOf(id));

        ResponseEntity<RestResponsePage<CommentDto>> response = getTemplate()
                .exchange("/api/v1/post/{id}/comment", HttpMethod.GET, new HttpEntity<>(""),
                        ParameterizedTypeReference.forType(TypeUtils.parameterize(
                                RestResponsePage.class, CommentDto.class)), params);

        Objects.requireNonNull(response.getBody()).forEach(c ->
            assertEquals(c.getCommentText(), commentDto.getCommentText())
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(7)
    void updateComment() {
        Long postId = repository.findAll().get(0).getId();
        Long commentId = commentRepository.findAll().get(0).getId();

        CommentDto commentDto = SourceDataFactory.commentDto(postId);
        commentDto.setCommentText("Update text");

        Map<String, String> params = new HashMap<>();
        params.put("size", "20");
        params.put("postId", String.valueOf(postId));
        params.put("commentId", String.valueOf(commentId));

        ResponseEntity<CommentDto> response = getTemplate()
                .exchange("/api/v1/post/{postId}/comment/{commentId}", HttpMethod.PUT,
                        new HttpEntity<>(commentDto), CommentDto.class, params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(8)
    void getSubComment() {
        Long postId = repository.findAll().get(0).getId();
        Comment comment = commentRepository.findAll().get(0);
        Long commentId = comment.getId();
        comment.setParentId(commentId);
        comment.setCommentText("Comment comment");
        comment.setId(null);
        commentRepository.save(comment);

        Map<String, String> params = new HashMap<>();
        params.put("size", "20");
        params.put("postId", String.valueOf(postId));
        params.put("commentId", String.valueOf(commentId));

        ResponseEntity<RestResponsePage<CommentDto>> response = getTemplate()
                .exchange("/api/v1/post/{postId}/comment/{commentId}/subcomment",
                        HttpMethod.GET, new HttpEntity<>(""),
                        ParameterizedTypeReference.forType(TypeUtils.parameterize(
                                RestResponsePage.class, CommentDto.class)), params);
        Objects.requireNonNull(response.getBody()).forEach(c ->
                assertEquals(c.getCommentText(), comment.getCommentText())
        );
    }

    @Test
    @Order(9)
    void createPostLike() {
        Long itemId = repository.findAll().get(0).getId();
        Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(itemId));
        ResponseEntity<LikeDto> response = getTemplate()
                .exchange("/api/v1/post/{itemId}/like", HttpMethod.POST,
                        new HttpEntity<>(""), LikeDto.class, params);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(10)
    void changeCommentLike() {
        Long postId = repository.findAll().get(0).getId();
        Long commentId = commentRepository.findAll().get(0).getId();
        Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(commentId));
        params.put("postId", String.valueOf(postId));
        ResponseEntity<LikeDto> response = getTemplate()
                .exchange("/api/v1/post/{postId}/comment/{itemId}/like",
                        HttpMethod.POST, new HttpEntity<>(""), LikeDto.class, params);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(11)
    void deleteCommentLike() {
        Long postId = repository.findAll().get(0).getId();
        Long commentId = commentRepository.findAll().get(0).getId();
        Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(commentId));
        params.put("postId", String.valueOf(postId));
        ResponseEntity<LikeDto> response = getTemplate()
                .exchange("/api/v1/post/{postId}/comment/{itemId}/like",
                        HttpMethod.DELETE, new HttpEntity<>(""), LikeDto.class, params);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(13)
    void deleteByIdComment() {
        Long commentId = commentRepository.findAll().get(1).getId();
        Long postId = repository.findAll().get(0).getId();
        Map<String, String> params = new HashMap<>();
        params.put("commentId", String.valueOf(commentId));
        params.put("postId", String.valueOf(postId));
        getTemplate().delete("/api/v1/post/{postId}/comment/{commentId}", params);
        assertTrue(commentRepository.findById(commentId).orElse(new Comment()).getIsDeleted());
    }

    @Test
    @Order(14)
    void deleteById() {
        Long postId = repository.findAll().get(0).getId();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(postId));
        getTemplate().delete("/api/v1/post/{id}", params);
        ResponseEntity<PostDto> response = getTemplate()
                .getForEntity("/api/v1/post/{id}", PostDto.class, postId);
        assertTrue(Objects.requireNonNull(response.getBody()).getIsDeleted());
    }
}