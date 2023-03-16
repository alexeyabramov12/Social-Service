package ru.skillbox.diplom.group33.social.service.mapper.account;

import org.mapstruct.*;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.auth.User;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class)
public interface AccountMapper {

    AccountDto convertToDto(Account account);

    Account convertToAccount(AccountDto accountDto);

    @Mapping(target = "isBlocked", constant = "false")
    @Mapping(target = "regDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "createOn", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "updateOn", expression = "java(ZonedDateTime.now())")
    Account userToAccount(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account convertToAccount(AccountDto accountDto, @MappingTarget Account account);
}