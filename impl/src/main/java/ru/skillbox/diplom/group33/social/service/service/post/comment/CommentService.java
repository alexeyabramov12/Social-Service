package ru.skillbox.diplom.group33.social.service.service.post.comment;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.socket.handler.NotificationHandler;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentType;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.exception.EntityNotFoundResponseStatusException;
import ru.skillbox.diplom.group33.social.service.mapper.post.comment.CommentMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment_;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;
import ru.skillbox.diplom.group33.social.service.repository.post.comment.CommentRepository;
import ru.skillbox.diplom.group33.social.service.service.post.like.LikeService;

import java.time.ZonedDateTime;

import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.COMMENT_COMMENT;
import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.POST_COMMENT;
import static ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.equal;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.getBaseSpecification;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper mapper;
    private final LikeService likeService;
    private final NotificationHandler notificationHandler;


    public Page<CommentDto> getAll(Long id, Pageable page) {
        log.info("IN CommentService get - getAll comments with post id: {}", id);
        Page<Comment> comments = commentRepository.findAll(getSpecificationForComment(id), page);
        return new PageImpl<>(comments.map(e-> {
            CommentDto dto = mapper.convertToDto(e);
            dto.setMyLike(likeService.getMyLike(dto.getId(), LikeType.COMMENT));
            return dto;
        }).toList(), page, comments.getTotalElements());
    }

    public CommentDto create(Long postId, CommentDto dto) {
        dto.setPostId(postId);
        if (dto.getParentId() == null) {
            dto.setCommentType(CommentType.POST);
            Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundResponseStatusException::new);
            post.setCommentsCount(post.getCommentsCount() + 1);
            postRepository.save(post);
            notificationHandler.sendNotification(post.getAuthorId(), getJwtUserIdFromSecurityContext(),
                    POST_COMMENT, "К Вашей записи ".concat("\"" + post.getTitle() + "\""));
            log.info("IN CommentService create post comment - postId: {}, dto: {}", postId, dto);
        } else {
            dto.setCommentType(CommentType.COMMENT);
            Comment comment = commentRepository.findById(dto.getParentId()).orElseThrow(EntityNotFoundResponseStatusException::new);
            comment.setCommentsCount(comment.getCommentsCount() + 1);
            commentRepository.save(comment);
            notificationHandler.sendNotification(comment.getAuthorId(), getJwtUserIdFromSecurityContext(),
                    COMMENT_COMMENT, "\"" + comment.getCommentText() + "\"");
            log.info("IN CommentService create sub comment - postId: {}, dto: {}", postId, dto);
        }
        return mapper.convertToDto(commentRepository.save(mapper.initEntity(dto)));
    }

    public CommentDto update(CommentDto dto, Long postId, Long commentId) {
        log.info("IN CommentService update - post with id: {} comment with id: {} comment to update: {}", postId, commentId, dto);
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundResponseStatusException::new);
        comment.setCommentText(dto.getCommentText().trim());
        comment.setTimeChanged(ZonedDateTime.now());
        log.info("IN CommentService update - comment updated. New comment: {}", comment);
        return mapper.convertToDto(commentRepository.save(comment));
    }

    public void deleteById(Long id, Long commentId) {
        Long parentId = commentRepository.findById(commentId).orElseThrow(EntityNotFoundResponseStatusException::new).getParentId();
        if (parentId == null) {
            Post post = postRepository.findById(id).orElseThrow(EntityNotFoundResponseStatusException::new);
            post.setCommentsCount(post.getCommentsCount() - 1);
            postRepository.save(post);
            log.info("IN CommentService deleteById post comment - postId: {} comment id: {}", id, commentId);
        } else {
            Comment parentComment = commentRepository.findById(parentId).orElseThrow(EntityNotFoundResponseStatusException::new);
            parentComment.setCommentsCount(parentComment.getCommentsCount() - 1);
            commentRepository.save(parentComment);
            log.info("IN CommentService deleteById sub comment - postId: {} comment id: {}", id, commentId);

        }
        commentRepository.deleteById(commentId);
    }

    public Page<CommentDto> getAllSubComment(Long postId, Long commentId, Pageable page) {
        log.info("IN CommentService getAllSubComment - get subcomments with comment id: {}", commentId);
        return commentRepository.findAll(getSpecificationForSubcomment(commentId), page).map(mapper::convertToDto);
    }

    private static Specification<Comment> getSpecificationForComment(Long id) {
        return getBaseSpecification(new CommentSearchDto())
                .and(equal(Comment_.parentId, null, true))
                .and(equal(Comment_.postId, id, true))
                .and(equal(Comment_.commentType, CommentType.POST, true))
                .and(equal(Comment_.isDeleted, false, true));
    }


    private static Specification<Comment> getSpecificationForSubcomment(Long id) {
        return getBaseSpecification(new CommentSearchDto())
                .and(equal(Comment_.parentId, id, true))
                .and(equal(Comment_.commentType, CommentType.COMMENT, true))
                .and(equal(Comment_.isDeleted, false, true));
    }


}

