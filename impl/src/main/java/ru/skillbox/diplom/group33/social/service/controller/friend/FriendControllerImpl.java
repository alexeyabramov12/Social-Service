package ru.skillbox.diplom.group33.social.service.controller.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.service.friend.FriendService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FriendControllerImpl implements FriendController {
    private final FriendService friendService;

    @Override
    public ResponseEntity<FriendDto> getById(Long id) {
        log.info("FriendControllerImpl : getById {}", id);
        return ResponseEntity.ok(friendService.getById(id));
    }

    @Override
    public ResponseEntity<Page<FriendDto>> getAll(FriendSearchDto searchDto, Pageable page) {
        log.info("FriendControllerImpl : getAll {}", searchDto);
        return ResponseEntity.ok(friendService.getAll(searchDto, page));
    }

    @Override
    public ResponseEntity<FriendDto> create(FriendDto dto) {
        log.info("FriendControllerImpl : create {}", dto);
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<FriendDto> update(FriendDto dto) {
        log.info("FriendControllerImpl : update {}", dto);
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        log.info("FriendControllerImpl : delete {}", id);
        friendService.delete(id);
        return ResponseEntity.ok(id);
    }

    @Override
    public ResponseEntity<List<FriendDto>> approveFriend(Long id) {
        log.info("FriendControllerImpl : approve {}", id);
        return ResponseEntity.ok(friendService.approveFriend(id));
    }

    @Override
    public void blockFriend(Long id) {
        log.info("FriendControllerImpl : block {}", id);
        friendService.blockFriend(id);
    }

    @Override
    public ResponseEntity<List<FriendDto>> addFriend(Long id) {
        log.info("FriendControllerImpl : add {}", id);
        return ResponseEntity.ok(friendService.addFriend(id));
    }

    @Override
    public ResponseEntity<FriendDto> subscribeFriend(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<FriendDto>> getRecommendations() {
        return ResponseEntity.ok(friendService.getRecommendations());
    }

    @Override
    public ResponseEntity<FriendSearchDto> getFriendsIds() {
        return null;
    }

    @Override
    public Long countFriendsOffers() {
        return friendService.getCount();
    }

    @Override
    public ResponseEntity<FriendSearchDto> getBlockedFriends() {
        return null;
    }

}
