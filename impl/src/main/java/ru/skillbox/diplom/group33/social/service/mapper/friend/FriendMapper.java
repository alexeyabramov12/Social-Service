package ru.skillbox.diplom.group33.social.service.mapper.friend;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.RecommendationFriendsDto;
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
    @Mapping(target = "id", source = "id")
    Friend friendToFriendFrom(AccountDto account);

    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_TO)")
    @Mapping(target = "fromAccountId", source = "id")
    @Mapping(target = "toAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
    @Mapping(target = "previousStatusCode", source = "statusCode")
    @Mapping(target = "id", source = "id")
    Friend friendToFriendTo(AccountDto account);

    Friend convertToEntity(FriendDto friendDto);

    FriendSearchDto convertToSearch(Friend friend);

    RecommendationFriendsDto convertToRecommendation(Friend friend);

//    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_FROM)")
//    @Mapping(target = "fromAccountId", source = "id")
//    @Mapping(target = "toAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
//    Friend userDtoToFriendFrom(UserDto userDto);
//
//    @Mapping(target = "statusCode", expression = "java(ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.REQUEST_TO)")
//    @Mapping(target = "fromAccountId", expression = "java(ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext())")
//    @Mapping(target = "toAccountId", source = "id")
//    Friend userDtoToFriendTo(UserDto userDto);

}
