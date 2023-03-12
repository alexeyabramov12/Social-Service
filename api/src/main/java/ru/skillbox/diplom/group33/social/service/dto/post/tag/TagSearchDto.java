
package ru.skillbox.diplom.group33.social.service.dto.post.tag;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TagSearchDto extends BaseSearchDto {

    private String name;
}
