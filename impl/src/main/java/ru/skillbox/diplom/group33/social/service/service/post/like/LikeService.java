package ru.skillbox.diplom.group33.social.service.service.post.like;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.mapper.post.like.LikeMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment;
import ru.skillbox.diplom.group33.social.service.model.post.like.Like;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;
import ru.skillbox.diplom.group33.social.service.repository.post.comment.CommentRepository;
import ru.skillbox.diplom.group33.social.service.repository.post.like.LikeRepository;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final LikeMapper mapper;


    public void changeLikeAmount(Long itemId, LikeType type, Integer amount) {
        switch (type) {
            case POST -> {
                Post post = postRepository.findById(itemId).orElseThrow();
                post.setLikeAmount(post.getLikeAmount() + amount);
                postRepository.save(post);
            }
            case COMMENT -> {
                Comment comment = commentRepository.findById(itemId).orElseThrow();
                comment.setLikeAmount(comment.getLikeAmount() + amount);
                commentRepository.save(comment);
            }
        }
    }

    public LikeDto changePostLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(getLoginUserId(), likeType, itemId)
                .orElse(mapper.initLikeDto(new LikeDto(), likeType, itemId));
        Optional<Post> post = postRepository.findById(itemId);
        if (like.getId() == null || like.getIsDeleted()) {
            like.setAuthorId(getLoginUserId());
            like.setType(likeType);
            like.setItemId(itemId);
            like.setIsDeleted(false);
            like.setTime(ZonedDateTime.now());
            changeLikeAmount(itemId, likeType, 1);
            if (isAuthor(post.get().getAuthorId())) {
                changeMyLike(post.get(), null, true);
            }
        } else {
            changeLikeAmount(itemId, likeType, -1);
            like.setIsDeleted(true);
            likeRepository.save(like);
            if (isAuthor(post.get().getAuthorId())) {
                changeMyLike(post.get(), null, false);
            }
        }
        return mapper.convertToDto(likeRepository.save(like));
    }

    public LikeDto changeCommentLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(getLoginUserId(), likeType, itemId)
                .orElse(mapper.initLikeDto(new LikeDto(), likeType, itemId));
        Optional<Comment> comment = commentRepository.findById(itemId);
        if (like.getId() == null) {
            changeLikeAmount(itemId, likeType, 1);
            if (isAuthor(comment.get().getAuthorId())) {
                changeMyLike(null, comment.get(), true);
            }
            return mapper.convertToDto(likeRepository.save(like));
        } else {
            like.setIsDeleted(!like.getIsDeleted());
            if (isAuthor(comment.get().getAuthorId())) {
                changeMyLike(null, comment.get(), !comment.get().getMyLike());
            }
            likeRepository.save(like);
            changeLikeAmount(itemId, likeType, like.getIsDeleted() ? -1 : 1);
        }
        return mapper.convertToDto(like);
    }


    public Boolean getMyLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(getLoginUserId(), likeType, itemId)
                .orElse(null);
        if (like == null) {
            return false;
        }
        return !like.getIsDeleted();
    }


    private boolean isAuthor(Long itemId) {
        return itemId.equals(getLoginUserId());
    }

    private void changeMyLike(Post post, Comment comment, boolean like) {
        if (post != null) {
            post.setMyLike(like);
            postRepository.save(post);
        } else if (comment != null) {
            comment.setMyLike(like);
            commentRepository.save(comment);
        }
    }

    public Long getLoginUserId() {
        return SecurityUtils.getJwtUserIdFromSecurityContext();
    }

}


