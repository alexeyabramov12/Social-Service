package ru.skillbox.diplom.group33.social.service.controller.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.controller.base.BaseController;
import ru.skillbox.diplom.group33.social.service.dto.auth.UserDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.RecommendationFriendsDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

import java.util.List;

@Tag(name = "Friend controller", description = "Операции для работы с друзьями пользователя")
@RequestMapping(PathConstant.URL + "friends")
public interface FriendController extends BaseController<FriendDto, FriendSearchDto> {
    @Override
    @Operation(summary = "Получить друга по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", description = "Внутрення ошибка", content = @Content(mediaType = ""))
            })
    @GetMapping("{accountId}")
    ResponseEntity<FriendDto> getById(@PathVariable Long accountId);

    @Override
    @Operation(summary = "Получить всех друзей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    @GetMapping()
    ResponseEntity<Page<FriendDto>> getAll(FriendSearchDto searchDto, Pageable page);

    @Override
    @Operation(summary = "??")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> create(@RequestBody FriendDto dto);

    @Override
    @Operation(summary = "Обновить друга")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> update(@RequestBody FriendDto dto);

    @Override
    @Operation(summary = "Удалить друга по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    @DeleteMapping("{id}")
    ResponseEntity deleteById(@PathVariable Long id);

    @PutMapping("{id}/approve")
    @Operation(summary = "Подтвердить дружбу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> approveFriend(@PathVariable Long id);

    @PutMapping("block/{id}")
    @Operation(summary = "Заблокировать друга по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> blockFriend(@PathVariable Long id);

    @PostMapping("{id}/request")
    @Operation(summary = "Добавить друга по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> addFriend(@PathVariable Long id);
    
    @PostMapping("subscribe/{id}")
    @Operation(summary = "Подписаться на друга по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> subscribeFriend(@PathVariable Long id);
    @GetMapping("/recommendations")
    @Operation(summary = "Получить рекомендации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<List<RecommendationFriendsDto>> getRecommendations();

    @GetMapping("friendId")
    @Operation(summary = "Получить id друзей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendSearchDto> getFriendsIds();

    @GetMapping("count")
    @Operation(summary = "Счетчик заявок в друзья")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendDto> countFriendsOffers();

    @GetMapping("blockFriendId")
    @Operation(summary = "Получить список заблокированных друзей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", description = "Не успешно", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Не авторизован", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))
    })
    ResponseEntity<FriendSearchDto> getBlockedFriends();
}
