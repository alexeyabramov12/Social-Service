package ru.skillbox.diplom.group33.social.service.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто поиска поста")
public class PostSearchDto extends BaseSearchDto {

   private List<Long> ids;
   private List<Long> accountIds;
   private List<Long> blockedIds;
   private String author;
   private String title;
   private String postText;
   private Boolean withFriends;
   private Set<String> tags;
   private ZonedDateTime dateFrom;
   private ZonedDateTime dateTo;

}
