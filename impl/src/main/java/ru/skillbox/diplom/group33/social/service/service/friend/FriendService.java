package ru.skillbox.diplom.group33.social.service.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.RecommendationFriendsDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;
import ru.skillbox.diplom.group33.social.service.exception.EntityNotFoundResponseStatusException;
import ru.skillbox.diplom.group33.social.service.mapper.friend.FriendMapper;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend_;
import ru.skillbox.diplom.group33.social.service.repository.friend.FriendRepository;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class
FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;


    public FriendDto getById(Long id) {

        log.info("FriendService: getById {}", id);
        return friendMapper.convertToDto(friendRepository.findById(id)
                .orElseThrow(EntityNotFoundResponseStatusException::new));
    }

    public Page<FriendDto> getAll(FriendSearchDto searchDto, Pageable page) {
        searchDto.setToAccountId(SecurityUtils.getJwtUserIdFromSecurityContext());
        log.info("FriendService: findAll");
        return friendRepository.findAll(getSpecification(searchDto), page)
                .map(friendMapper::convertToDto);
    }

    public FriendDto create(FriendDto friendDto) {
        log.info("FriendService: create {}", friendDto);
        return friendMapper.convertToDto(friendRepository.save(friendMapper.convertToEntity(friendDto)));

    }

    public FriendDto update(FriendDto friendDto) {
        log.info("FriendService: update {}", friendDto);
        return friendMapper.convertToDto(friendRepository.save(friendMapper.convertToEntity(friendDto)));
    }

    public void delete(Long id) {
        Friend friend = friendRepository.findById(id).orElseThrow(NoSuchElementException::new);
        friend.setStatusCode(StatusCode.NONE);
        friend.setPreviousStatusCode(StatusCode.FRIEND);
        log.info("FriendService: delete by id {}", id);
        friendRepository.save(friend);
    }

    public List<RecommendationFriendsDto> getRecommendations() {
        log.info("FriendService: getRecommendations");
        FriendSearchDto friendSearchDto = new FriendSearchDto();
        friendSearchDto.setToAccountId(SecurityUtils.getJwtUserIdFromSecurityContext());
        friendSearchDto.setStatusCode(StatusCode.RECOMMENDATION);

        return friendRepository.
                findAll(getSpecification(friendSearchDto))
                .stream().map(friendMapper::convertToRecommendation).collect(Collectors.toList());
    }

    public FriendDto blockFriend(Long id) {
        log.info("FriendService: block by id {}", id);
        Friend friend = friendRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (friend.getStatusCode() == StatusCode.BLOCKED) {
            friend.setStatusCode(friend.getStatusCode());
            friend.setPreviousStatusCode(StatusCode.BLOCKED);
        } else {
            friend.setPreviousStatusCode(friend.getStatusCode());
            friend.setStatusCode(StatusCode.BLOCKED);
        }

        friendRepository.save(friend);
        return friendMapper.convertToDto(friend);
    }

    public FriendDto approveFriend(Long id) {
        log.info("FriendService: approve by id {}", id);
        Friend friend = friendRepository.findById(id).orElseThrow(NoSuchElementException::new);
        friend.setStatusCode(StatusCode.FRIEND);
        friend.setPreviousStatusCode(StatusCode.NONE);
        return friendMapper.convertToDto(friend);
    }


    public FriendDto addFriend(Long id) {
        log.info("FriendService: add by id {}", id);
        Friend friend = friendRepository.findById(id).orElseThrow(NoSuchElementException::new);

        Friend friendTo = friendMapper.friendToFriendTo(friend);

        Friend friendFrom = friendMapper.friendToFriendFrom(friend);

        friendRepository.save(friendTo);
        friendRepository.save(friendFrom);

        return friendMapper.convertToDto(friendTo);
    }

    private List<Friend> listFriendsToAdd(Long id) {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setToAccountId(id);
        dto.setFromAccountId(SecurityUtils.getJwtUserIdFromSecurityContext());

        return friendRepository.findAll(getSpecification(dto));
    }

    private List<Friend> listFriendsToApprove(Long id) {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setToAccountId(SecurityUtils.getJwtUserIdFromSecurityContext());
        dto.setFromAccountId(id);

        return friendRepository.findAll(getSpecification(dto));
    }

    private List<Friend> getCurrentFriendsByAccountId(Long id) {
        List<Friend> friendList = new ArrayList<>();
        friendList.addAll(listFriendsToAdd(id));
        friendList.addAll(listFriendsToApprove(id));
        return friendList;
    }

    private Specification<Friend> getSpecification(FriendSearchDto friendSearchDto) {
        return getBaseSpecification(friendSearchDto)
                .and(equal(Friend_.statusCode, friendSearchDto.getStatusCode(), true))
                .and(equal(Friend_.toAccountId, friendSearchDto.getToAccountId(), true))
                .and(equal(Friend_.fromAccountId, friendSearchDto.getFromAccountId(), true))
                .and(in(Friend_.id, friendSearchDto.getIds(), true));
    }
}
