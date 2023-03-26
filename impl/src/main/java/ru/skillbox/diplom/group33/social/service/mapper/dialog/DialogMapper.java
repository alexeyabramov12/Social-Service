package ru.skillbox.diplom.group33.social.service.mapper.dialog;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.dialog.DialogDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.DialogsRs;
import ru.skillbox.diplom.group33.social.service.model.dialog.Dialog;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.util.List;

@Mapper(componentModel = "spring", imports = SecurityUtils.class)
public interface DialogMapper {


    @Mapping(target = "unreadCount", ignore = true)
    @Mapping(target = "conversationPartner", ignore = true)
    DialogDto convertToDto(Dialog dialog);

    @Mapping(target = "data", expression = "java(dtoList)")
    @Mapping(target = "total", expression = "java((long) dtoList.size())")
    @Mapping(target = "offSet", expression = "java(offset)")
    @Mapping(target = "perPage", expression = "java(itemPerPage)")
    @Mapping(target = "currentUserId", expression = "java(SecurityUtils.getJwtUserIdFromSecurityContext())")
    DialogsRs initDialogRs(String error, String errorDescription, Long timeStamp, List<DialogDto> dtoList, Integer offset, Integer itemPerPage);

}
