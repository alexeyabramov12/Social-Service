package ru.skillbox.diplom.group33.social.service.mapper.post;

import org.mapstruct.Mapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toPostDto(Post post);

    Post toPost(PostDto postDto);
}
