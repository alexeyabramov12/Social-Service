package ru.skillbox.diplom.group33.social.service.mapper.post.tag;

import org.mapstruct.Mapper;
import ru.skillbox.diplom.group33.social.service.dto.post.tag.TagDto;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto convertToDto(Tag tag);

    Tag convertToEntity(TagDto tagDto);

    Set<String> convertSetTagsToSetString(Set<Tag> tags);

    default String mapChildSourceToString(Tag tag) {
        return tag.getName();
    }

}
