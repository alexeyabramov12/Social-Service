package ru.skillbox.diplom.group33.social.service.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseSearchDto implements Serializable {

    private Long id;
    private Boolean isDeleted;
}