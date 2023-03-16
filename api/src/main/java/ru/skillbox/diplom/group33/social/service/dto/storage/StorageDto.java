package ru.skillbox.diplom.group33.social.service.dto.storage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Дто хранилища")
public class StorageDto {
    @Schema(description = "Адрес картинки")
    private String photoPath;
    @Schema(description = "Имя картинки")
    private String photoName;
}
