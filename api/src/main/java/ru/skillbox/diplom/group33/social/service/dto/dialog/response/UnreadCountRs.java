package ru.skillbox.diplom.group33.social.service.dto.dialog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.base.BaseResponse;
import ru.skillbox.diplom.group33.social.service.dto.dialog.unreadCountDto.UnreadCountDto;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ответ на зпарос получения количества непрочитанныйх сообщений диалога")
public class UnreadCountRs extends BaseResponse {

    public UnreadCountRs(String error, String errorDescription, Long timeStamp) {
        super(error, errorDescription, timeStamp);
    }
    @Schema(description = "Дто количества непрочитанных сообщений диалога")
    private UnreadCountDto data;

}
