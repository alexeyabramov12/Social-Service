package ru.skillbox.diplom.group33.social.service.mapper.post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostType;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, SecurityUtils.class, PostType.class})
public interface PostMapper {

    @Mapping(target = "tags", qualifiedByName = "setTagsToSetString")
    PostDto convertToDto(Post post);

    @Mapping(target = "tags", ignore = true)
    Post convertToEntity(PostDto postDto);


    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "publishDate", expression = "java(dto.getPublishDate() == null ? ZonedDateTime.now() : dto.getPublishDate())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "authorId", expression = "java(SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "postType", expression = "java(PostType.POSTED)")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(target = "likeAmount", constant = "0")
    @Mapping(target = "commentsCount", constant = "0")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "myLike", constant = "false")
    @Mapping(target = "title", expression = "java(dto.getTitle().trim())")
    @Mapping(target = "postText", expression = "java(dto.getPostText().trim())")
    Post initEntity(PostDto dto);

    @Mapping(target = "id", expression = "java(post.getId())")
    @Mapping(target = "time", expression = "java(post.getTime())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "publishDate", expression = "java(post.getPublishDate())")
    @Mapping(target = "authorId", expression = "java(post.getAuthorId())")
    @Mapping(target = "postType", expression = "java(post.getPostType())")
    @Mapping(target = "isDeleted", expression = "java(post.getIsDeleted())")
    @Mapping(target = "isBlocked", expression = "java(post.getIsBlocked())")
    @Mapping(target = "likeAmount", expression = "java(post.getLikeAmount())")
    @Mapping(target = "commentsCount", expression = "java(post.getCommentsCount())")
    @Mapping(target = "myLike", expression = "java(post.getMyLike())")
    @Mapping(target = "imagePath", expression = "java(post.getImagePath())")
    @Mapping(target = "title", expression = "java(dto.getTitle().trim())")
    @Mapping(target = "postText", expression = "java(dto.getPostText().trim())")
    @Mapping(target = "tags", expression = "java(post.getTags())")
    Post updateEntity(PostDto dto, Post post);

    @Named("setTagsToSetString")
    default Set<String> convertSetTagsToSetString(Set<Tag> tags) {
        Set<String> stringSet = new HashSet<>();
        tags.forEach(t -> stringSet.add(t.getName()));
        return stringSet;
    }
}
