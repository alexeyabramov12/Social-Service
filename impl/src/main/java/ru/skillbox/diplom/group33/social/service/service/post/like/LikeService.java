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

    public LikeDto createLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        if (like.getId() == null || like.getIsDeleted()) {
            like.setAuthorId(SecurityUtils.getJwtUserIdFromSecurityContext());
            like.setType(likeType);
            like.setItemId(itemId);
            like.setIsDeleted(false);
            like.setTime(ZonedDateTime.now());
            changeLikeAmount(itemId, likeType, 1);

        }
        return mapper.convertToDto(likeRepository.save(like));
    }

    public LikeDto changeCommentLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        if (like.getId() == null || like.getIsDeleted()) {
            like.setAuthorId(SecurityUtils.getJwtUserIdFromSecurityContext());
            like.setType(likeType);
            like.setItemId(itemId);
            like.setIsDeleted(false);
            like.setTime(ZonedDateTime.now());
            changeLikeAmount(itemId, likeType, 1);
        }
        return mapper.convertToDto(likeRepository.save(like));
    }
    public LikeDto changeCommentLike1(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        Comment comment = commentRepository.findById(itemId).orElse(new Comment());
        boolean isAuthor = comment.getAuthorId().equals(SecurityUtils.getJwtUserIdFromSecurityContext());
        if (like.getId() == null) {
            //Like like = mapper.initLikeDto(new LikeDto(), likeType, itemId);
            changeLikeAmount(itemId, likeType, 1);
            changeMyLike(null, comment, true);
            return mapper.convertToDto(likeRepository.save(like));
        } else {
            like.setIsDeleted(!like.getIsDeleted());
            if (isAuthor) {
                changeMyLike(null, comment, false);
            }
            likeRepository.save(like);
            changeLikeAmount(itemId, likeType, like.getIsDeleted() ? -1 : 1);
        }
        return mapper.convertToDto(like);
    }

    public void deleteLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        if (like.getId() != null) {
            changeLikeAmount(itemId, likeType, -1);
            like.setIsDeleted(true);
            likeRepository.save(like);
        }
    }

    public Boolean getMyLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(null);
        if (like == null) {
            return false;
        }
        return !like.getIsDeleted();
    }



    /*public LikeDto createLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        Post post = postRepository.findById(itemId).orElse(new Post());
        boolean isAuthor = post.getAuthorId().equals(SecurityUtils.getJwtUserIdFromSecurityContext());
        if (like.getId() == null) {
            LikeDto likeDto = mapper.initLikeDto(new LikeDto(), likeType, itemId);
            if (isAuthor) {
                changeMyLike(post, null, true);
            }
            changeLikeAmount(itemId, likeType, 1);
            return mapper.convertToDto(likeRepository.save(mapper.convertToEntity(likeDto)));
        } else {
            like.setIsDeleted(false);
            if (isAuthor) {
                changeMyLike(post, null, true);
            }
            likeRepository.save(like);
            changeLikeAmount(itemId, likeType, 1);
        }
        return mapper.convertToDto(like);
    }

    public LikeDto changeCommentLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        Comment comment = commentRepository.findById(itemId).orElse(new Comment());
        boolean isAuthor = comment.getAuthorId().equals(SecurityUtils.getJwtUserIdFromSecurityContext());
        if (like.getId() == null) {
            LikeDto likeDto = mapper.initLikeDto(new LikeDto(), likeType, itemId);
            changeLikeAmount(itemId, likeType, 1);
            changeMyLike(null, comment, true);
            return mapper.convertToDto(likeRepository.save(mapper.convertToEntity(likeDto)));
        } else {
            like.setIsDeleted(!like.getIsDeleted());
            if (isAuthor) {
                changeMyLike(null, comment, false);
            }
            likeRepository.save(like);
            changeLikeAmount(itemId, likeType, like.getIsDeleted() ? -1 : 1);
        }
        return mapper.convertToDto(like);
    }


    public void deleteLike(Long itemId, LikeType likeType) {
        Like like = likeRepository
                .findByAuthorIdAndTypeAndItemId(SecurityUtils.getJwtUserIdFromSecurityContext(), likeType, itemId)
                .orElse(new Like());
        Post post = postRepository.findById(itemId).orElse(new Post());
        boolean isAuthor = post.getAuthorId().equals(SecurityUtils.getJwtUserIdFromSecurityContext());
        if (like.getId() != null) {
            changeLikeAmount(itemId, likeType, -1);
            if (isAuthor) {
                changeMyLike(post, null, false);
            }
            like.setIsDeleted(true);
            likeRepository.save(like);
        }
    }

    */
    private void changeMyLike(Post post, Comment comment, boolean like) {
        if (post != null) {
            post.setMyLike(like);
            postRepository.save(post);
        } else if (comment != null) {
            comment.setMyLike(like);
            commentRepository.save(comment);
        }
    }

}


