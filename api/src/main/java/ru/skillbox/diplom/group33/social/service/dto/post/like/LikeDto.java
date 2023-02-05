package ru.skillbox.diplom.group33.social.service.dto.post.like;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;
@Data
public class LikeDto extends BaseDto {

    private Long authorId;
    private Long itemId;
    private LikeType type;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime time;
}
