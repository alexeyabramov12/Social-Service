package ru.skillbox.diplom.group33.social.service.mapper.post.like;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.model.post.like.Like;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, SecurityUtils.class, LikeType.class})
public interface LikeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", constant = "false")
    LikeDto convertToDto(Like like);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", constant = "false")
    Like convertToEntity(LikeDto likeDto);

    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "authorId", expression = "java(SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "itemId", source = "itemId")
    @Mapping(target = "isDeleted", expression = "java(false)")
    Like initLikeDto(LikeDto likeDto, LikeType type, Long itemId);

}
