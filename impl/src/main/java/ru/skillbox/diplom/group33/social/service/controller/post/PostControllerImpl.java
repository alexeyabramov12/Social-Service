package ru.skillbox.diplom.group33.social.service.controller.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.dto.storage.StorageDto;
import ru.skillbox.diplom.group33.social.service.service.post.PostService;
import ru.skillbox.diplom.group33.social.service.service.post.comment.CommentService;
import ru.skillbox.diplom.group33.social.service.service.post.like.LikeService;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;


    //_______________________________________POST______________________________________________________________________


    @Override
    public ResponseEntity<PostDto> getById(Long id) {
        log.info("IN PostController getById - id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getById(id));
    }

    @Override
    public ResponseEntity<Page<PostDto>> getAll(PostSearchDto searchDto, Pageable page) {
        log.info("IN PostController getAll - search dto: {}", searchDto);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll(searchDto, page));
    }

    @Override
    public ResponseEntity<PostDto> create(PostDto dto) {
        log.info("IN PostController create - dto: {}", dto);
        return ResponseEntity.status(HttpStatus.OK).body(postService.create(dto));
    }

    @Override
    public ResponseEntity<PostDto> update(PostDto dto) {
        return null;
    }


    @Override
    public ResponseEntity<PostDto> update(Long id, PostDto dto) {
        log.info("IN PostController update - id: {} dto: {}", id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(id, dto));
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        log.info("IN PostController deleteById - id: {}", id);
        postService.deleteById(id);
        return ResponseEntity.ok("Post - id: {" + id + "} DELETED");
    }


    //_______________________________________COMMENT___________________________________________________________________


    @Override
    public ResponseEntity<Page<CommentDto>> getComments(Long id, Pageable page) {
        log.info("IN PostController getAllComment - get comments withs post id: {}", id);
        return ResponseEntity.ok(commentService.getAll(id, page));
    }

    @Override
    public ResponseEntity<CommentDto> createComment(Long id, CommentDto dto) {
        log.info("IN PostController createComment - dto: {}", dto);
        return ResponseEntity.ok(commentService.create(id, dto));
    }

    @Override
    public ResponseEntity<CommentDto> updateComment(CommentDto dto, Long postId, Long commentId) {
        log.info("IN PostController updateComment - dto: {}", dto);
        return ResponseEntity.ok(commentService.update(dto, postId, commentId));
    }

    @Override
    public ResponseEntity deleteByIdComment(Long postId, Long commentId) {
        log.info("IN PostController deleteByIdComment - id: {}", commentId);
        commentService.deleteById(postId, commentId);
        return ResponseEntity.ok("Comment - id: {" + commentId + "} DELETED");
    }

    @Override
    public ResponseEntity<Page<CommentDto>> getSubcomment(Long postId, Long commentId, Pageable page) {
        log.info("IN PostController getSubComment - id: {}", commentId);
        return ResponseEntity.ok(commentService.getAllSubComment(postId, commentId, page));
    }


    //_______________________________________LIKE______________________________________________________________________


    @Override
    public ResponseEntity<LikeDto> createPostLike(Long itemId) {
        log.info("IN PostController createPostLike - post id: {}", itemId);
        return ResponseEntity.ok(likeService.changePostLike(itemId, LikeType.POST));
    }

    @Override
    public ResponseEntity<StorageDto> addPostPhoto(MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(postService.addPostPhoto(file));
    }

    @Override
    public ResponseEntity deletePostLike(Long itemId) {
        log.info("IN PostController deletePostLike - post id: {}", itemId);
        likeService.changePostLike(itemId, LikeType.POST);
        return ResponseEntity.ok("like deleted - post id: {" + itemId + "}");
    }
    @Override
    public ResponseEntity<LikeDto> changeCommentLike(Long itemId, Long commentId) {
        log.info("IN PostController createCommentLike - post id: {}", itemId);
        return ResponseEntity.ok(likeService.changeCommentLike(commentId, LikeType.COMMENT));
    }

    @Override
    public ResponseEntity deleteCommentLike(Long itemId, Long commentId) {
        log.info("IN PostController deleteCommentLike - post id: {}", itemId);
        return ResponseEntity.ok(likeService.changeCommentLike(commentId, LikeType.COMMENT));
    }



}