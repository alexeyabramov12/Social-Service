package ru.skillbox.diplom.group33.social.service.mapper.friend;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.RecommendationFriendsDto;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendMapper {


    FriendDto convertToDto(Friend friend);
    List<FriendDto> convertToDtoList(List<Friend> friendList);
    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_FROM)")
    @Mapping(target = "fromAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "toAccountId", source = "id")
    @Mapping(target = "previousStatusCode", source = "statusCode")

    Friend friendToFriendFrom(AccountDto account);

    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_TO)")
    @Mapping(target = "fromAccountId", source = "id")
    @Mapping(target = "toAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "previousStatusCode", source = "statusCode")

    Friend friendToFriendTo(AccountDto account);

    Friend convertToEntity(FriendDto friendDto);

    Account friendToAccount(Friend friend);

    RecommendationFriendsDto convertToRecommendation(Friend friend);

    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.FRIEND)")
    @Mapping(target = "fromAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "toAccountId", source = "id")
    @Mapping(target = "previousStatusCode", source = "statusCode")

    Friend friendToFriendFromApprove(AccountDto account);

    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.FRIEND)")
    @Mapping(target = "fromAccountId", source = "id")
    @Mapping(target = "toAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "previousStatusCode", source = "statusCode")
    Friend friendToFriendToApprove(AccountDto account);

    FriendDto accountDtoToFriendDto(AccountDto accountDto);
}
