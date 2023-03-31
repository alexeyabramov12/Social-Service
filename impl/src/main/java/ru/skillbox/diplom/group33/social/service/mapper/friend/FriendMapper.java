package ru.skillbox.diplom.group33.social.service.mapper.friend;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendMapper {
    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_FROM)")
    @Mapping(target = "toAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "birthDay", source = "birthDate")
    Friend accountToFriendRequestTo(Account account);

    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_TO)")
    @Mapping(target = "fromAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "toAccountId", source = "id")
    @Mapping(target = "birthDay", source = "birthDate")
    Friend accountToFriendRequestFrom(Account account);

    @Mapping(target = "previousStatusCode", source = "statusCode")
    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.FRIEND)")
    Friend friendToApprovedFriend(Friend friend);

    @Mapping(target = "previousStatusCode", source = "statusCode")
    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.BLOCKED)")
    @Mapping(target = "toAccountId", source = "id")
    @Mapping(target = "fromAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "birthDay", source = "birthDay")
    Friend friendToBlockedFriend(Friend friend);

    FriendDto convertToDto(Friend friend);

    List<FriendDto> convertToDtoList(List<Friend> friendList);

    @Mapping(target = "birthDay", source = "birthDate")
    Friend accountToFriend(Account account);

    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_TO)")
    @Mapping(target = "fromAccountId", source = "id")
    @Mapping(target = "toAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "previousStatusCode", source = "statusCode")
    @Mapping(target = "birthDay", source = "birthDate")
    Friend friendToFriendTo(AccountDto account);

    Friend convertToEntity(FriendDto friendDto);

    FriendDto accountDtoToFriendDto(AccountDto accountDto);
}
