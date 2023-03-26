package ru.skillbox.diplom.group33.social.service.dto.dialog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group33.social.service.dto.dialog.DialogDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ответ на запрос получения списка диалогов пользователя")
public class DialogsRs extends BaseResponse {

    public DialogsRs(String error, String errorDescription, Long timeStamp) {
        super(error, errorDescription, timeStamp);
    }

    @Schema(description = "Колличество диалогов пользователя в списке", example = "10")
    private Long total;
    @Schema(description = "Отступ к начала списка", example = "0")
    private Integer offSet;
    @Schema(description = "Колличество диалогов пользоателя на страницу", example = "20")
    private Integer perPage;
    @Schema(description = "Id текущего пользователя", example = "55")
    private Long currentUserId;
    @Schema(description = "Список диалогов пользователя")
    private List<DialogDto> data;
}
