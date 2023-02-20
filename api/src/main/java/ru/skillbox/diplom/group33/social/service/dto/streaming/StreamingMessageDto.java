package ru.skillbox.diplom.group33.social.service.dto.streaming;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StreamingMessageDto<T> {

    @Schema(description = "Тип сообщения", example = "NOTIFICATION")
    private String type;
    @Schema(description = "Id целевого получателя", example = "232")
    private Long accountId;
    @Schema(description = "Данные сообщения", example = "NotificationDto: {time: 07 Mar 2023 21:20:25" +
            " Moscow/Europe, authorId: 989, userId: 232, content: Заголовок поста, notificationType: " +
            "POST, isStatusSent: true}")
    private T data;
}
