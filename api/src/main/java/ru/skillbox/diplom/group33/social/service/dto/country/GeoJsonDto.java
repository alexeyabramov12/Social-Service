package ru.skillbox.diplom.group33.social.service.dto.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Дто стран с городами с сайта hh.ru")
public class GeoJsonDto {

    @Schema(description = "Идентификатор объекта area(страны или города)", example = "42352")
    Long id;
    @Schema(description = "Родительский идентификатор объекта area(страны или города), при наличии", example = "62569")
    @JsonProperty("parent_id")
    Long parentId;
    @Schema(description = "Имя объекта area(страны или города)", example = "62569")
    String name;
    @Schema(description = "Список дочерних объектов(областей или городов)", example = "Россия: [Москва, Санкт-Петербург, Новосйойрск]")
    GeoJsonDto[] areas;
}
