package ru.skillbox.diplom.group33.social.service.dto.dialog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.base.BaseResponse;
import ru.skillbox.diplom.group33.social.service.dto.dialog.setStatusMessageDto.SetStatusMessageReadDto;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ответ на зпарос пометить сообщение как прочитанное")
public class StatusMessageReadRs extends BaseResponse {

    public StatusMessageReadRs(String error, String errorDescription, Long timeStamp) {
        super(error, errorDescription, timeStamp);
    }

    @Schema(description = "Дто сообщения о выполнении")
    private SetStatusMessageReadDto data;


}
