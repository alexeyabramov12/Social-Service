package ru.skillbox.diplom.group33.social.service.dto.dialog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group33.social.service.dto.dialog.DialogDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto.MessageShortDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ответ на запрос получения списка сообщений в диалоге")
public class MessageRs extends BaseResponse {

    public MessageRs(String error, String errorDescription, Long timeStamp) {
        super(error, errorDescription, timeStamp);
    }

    @Schema(description = "Колличество сообщений диалога в списке", example = "10")
    private Long total;
    @Schema(description = "Отступ к начала списка", example = "0")
    private Integer offSet;
    @Schema(description = "Колличество сообщений диалога на страницу", example = "20")
    private Integer perPage;
    @Schema(description = "Список сообщений диалога")
    private List<MessageShortDto> data;


}
