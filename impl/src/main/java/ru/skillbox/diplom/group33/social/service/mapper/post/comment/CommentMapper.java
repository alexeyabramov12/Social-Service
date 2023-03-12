package ru.skillbox.diplom.group33.social.service.mapper.post.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, SecurityUtils.class})
public interface CommentMapper {


    CommentDto convertToDto(Comment comment);

    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    Comment convertToEntity(CommentDto commentDto);

    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "authorId", expression = "java(SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "commentText", expression = "java(commentDto.getCommentText().trim())")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(target = "likeAmount", constant = "0")
    @Mapping(target = "myLike", constant = "false")
    @Mapping(target = "commentsCount", constant = "0")
    Comment initEntity(CommentDto commentDto);

}