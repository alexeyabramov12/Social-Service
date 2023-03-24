package ru.skillbox.diplom.group33.social.service.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;
import ru.skillbox.diplom.group33.social.service.exception.EntityNotFoundResponseStatusException;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.mapper.friend.FriendMapper;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend_;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.repository.friend.FriendRepository;

import java.util.*;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public FriendDto getById(Long id) {

        log.info("FriendService: getById {}", id);
        return friendMapper.convertToDto(friendRepository.findById(id)
                .orElseThrow(EntityNotFoundResponseStatusException::new));
    }

    public Page<FriendDto> getAll(FriendSearchDto searchDto, Pageable page) {
        log.info("FriendService: findAll");

        searchDto.setFromAccountId(getJwtUserIdFromSecurityContext());
        searchDto.setIsDeleted(false);

        List<Friend> friendList = friendRepository.findAll(getSpecification(searchDto));
        List<FriendDto> friendsToShow = new ArrayList<>();

        if (searchDto.getStatusCode() == null) {
            searchDto.setStatusCode(StatusCode.NONE);
        }

        for (Friend friend : friendList) {
            if (searchDto.getStatusCode().equals(StatusCode.BLOCKED) || !friend.getStatusCode().equals(StatusCode.BLOCKED)) {
                FriendDto friendDto = friendMapper
                        .accountDtoToFriendDto(accountMapper
                                .convertToDto(accountRepository.getReferenceById(friend.getToAccountId())));
                friendDto.setStatusCode(friend.getStatusCode());
                friendsToShow.add(friendDto);
            }
        }
        return new PageImpl<>(friendsToShow, page, friendsToShow.size());
    }

    public FriendDto create(FriendDto friendDto) {
        log.info("FriendService: create {}", friendDto);
        return friendMapper.convertToDto(friendRepository.save(friendMapper.convertToEntity(friendDto)));
    }

    public FriendDto update(FriendDto friendDto) {
        log.info("FriendService: update {}", friendDto);
        return friendMapper.convertToDto(friendRepository.save(friendMapper.convertToEntity(friendDto)));
    }

    public Long getCount() {
        FriendSearchDto friend = new FriendSearchDto(StatusCode.REQUEST_TO, getJwtUserIdFromSecurityContext());
        friend.setIsDeleted(false);
        return (long) friendRepository
                .findAll(getSpecification(friend)).size();

    }

    public void delete(Long id) {
        log.info("FriendService: delete by id {}", id);
        List<Friend> friendList = friendRepository
                .findAll(getSpecification(new FriendSearchDto(getJwtUserIdFromSecurityContext(), id)));
        friendList.addAll(friendRepository
                .findAll(getSpecification(new FriendSearchDto(id, getJwtUserIdFromSecurityContext()))));

        if (!friendList.isEmpty()) {
            friendRepository.deleteAll(friendList);
        }
    }

    public List<FriendDto> getRecommendations() {
        log.info("FriendService: getRecommendations");
        createRecommendations();

        FriendSearchDto friendSearchDto = new FriendSearchDto();
        friendSearchDto.setToAccountId(getJwtUserIdFromSecurityContext());
        friendSearchDto.setStatusCode(StatusCode.RECOMMENDATION);
        friendSearchDto.setIsDeleted(false);

        return friendRepository.
                findAll(getSpecification(friendSearchDto))
                .stream().map(friendMapper::convertToDto).collect(Collectors.toList());
    }

    public void blockFriend(Long id) {
        log.info("FriendService: block by id {}", id);

        List<Friend> blocked = getBlockedFriends(id);
        List<Friend> invoices = getCurrentInvoicesByAccountId(id);
        if (blocked.isEmpty()) {
            friendRepository.save(new Friend(getJwtUserIdFromSecurityContext(), StatusCode.BLOCKED, id));
            friendRepository.deleteAll(invoices);
        } else {
            friendRepository.deleteAll(blocked);
        }
    }
    private List<Friend> getBlockedFriends(Long id) {
        return friendRepository
                .findAll(getSpecification(
                        new FriendSearchDto(getJwtUserIdFromSecurityContext(), StatusCode.BLOCKED, id, false)));
    }

    public List<FriendDto> approveFriend(Long id) {
        log.info("FriendService: approve by id {}", id);
        List<Friend> friendToApprove = getCurrentInvoicesByAccountId(id);

        if (friendToApprove.isEmpty()) {
            return createApproves(id);
        }

        for (Friend friend : friendToApprove)
            if (friend.getStatusCode() == StatusCode.REQUEST_TO
                    || friend.getStatusCode() == StatusCode.REQUEST_FROM
                    || friend.getStatusCode() == StatusCode.RECOMMENDATION) {
                friendRepository.delete(friend);
            }

        return createApproves(id);
    }

    private List<FriendDto> createApproves(Long id) {
        Long myId = getJwtUserIdFromSecurityContext();

        List<Friend> listFriend = new ArrayList<>();
        if (alreadyFriends(id).isEmpty()) {
            listFriend.add(new Friend(myId, StatusCode.FRIEND, id, StatusCode.REQUEST_FROM));
            listFriend.add(new Friend(id, StatusCode.FRIEND, myId, StatusCode.REQUEST_TO));
            friendRepository.saveAll(listFriend);
            return friendMapper.convertToDtoList(listFriend);
        }

        return friendMapper.convertToDtoList(alreadyFriends(id));
    }


    public List<FriendDto> addFriend(Long id) {
        log.info("FriendService: add by id {}", id);

        Friend friend = friendRepository.findById(id).orElse(null);
        if (friend != null && friend.getStatusCode() == StatusCode.RECOMMENDATION) {
            Friend friend1 = friendRepository
                    .findAll(getSpecification(new FriendSearchDto(friend.getToAccountId(), StatusCode.RECOMMENDATION,
                            friend.getFromAccountId()))).get(0);
            List<Friend> listFriend = new ArrayList<>();
            listFriend.add(new Friend(friend.getFromAccountId(), StatusCode.REQUEST_FROM, friend.getToAccountId()));
            listFriend.add(new Friend(friend.getToAccountId(), StatusCode.REQUEST_TO, friend.getFromAccountId()));

            friendRepository.deleteAll(friendRepository
                    .findAll(getSpecification(new FriendSearchDto(friend.getFromAccountId(), friend.getToAccountId()))));
            friendRepository.deleteAll(friendRepository
                    .findAll(getSpecification(new FriendSearchDto(friend.getToAccountId(), friend.getFromAccountId()))));
            friendRepository.delete(friend);
            friendRepository.delete(friend1);
            friendRepository.saveAll(listFriend);

            return friendMapper.convertToDtoList(listFriend);
        }
        List<Friend> friendList = getCurrentInvoicesByAccountId(id);
        if (friendList.isEmpty()) {
            return createInvoices(id);
        }
        return friendMapper.convertToDtoList(friendList);
    }

    private List<Friend> getCurrentInvoicesByAccountId(Long id) {
        List<Friend> list = listFriendsToAdd(id);
        list.addAll(listFriendsToApprove(id));
        return list;
    }
    private List<FriendDto> createInvoices(Long id) {
        Long myId = getJwtUserIdFromSecurityContext();

        if (alreadySendRequests(id).isEmpty()) {
            List<Friend> listFriend = new ArrayList<>();
            listFriend.add(new Friend(myId, StatusCode.REQUEST_TO, id));
            listFriend.add(new Friend(id, StatusCode.REQUEST_FROM, myId));
            friendRepository.saveAll(listFriend);

            return friendMapper.convertToDtoList(listFriend);
        }
        return friendMapper.convertToDtoList(alreadySendRequests(id));
    }

    private List<Friend> alreadySendRequests(Long id) {
        List<Friend> listFriend = friendRepository.findAll(getSpecification(new FriendSearchDto(getJwtUserIdFromSecurityContext(), StatusCode.REQUEST_TO, id, false)));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_TO, getJwtUserIdFromSecurityContext(), false))));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(getJwtUserIdFromSecurityContext(), StatusCode.REQUEST_FROM, id, false))));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_FROM, getJwtUserIdFromSecurityContext(), false))));
        return listFriend;
    }

    private List<Friend> alreadyFriends(Long id) {
        List<Friend> listFriend = friendRepository.findAll(getSpecification(new FriendSearchDto(getJwtUserIdFromSecurityContext(), StatusCode.FRIEND, id, false)));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.FRIEND, getJwtUserIdFromSecurityContext(), false))));
        return listFriend;
    }

    private List<Friend> listFriendsToAdd(Long id) {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setFromAccountId(getJwtUserIdFromSecurityContext());
        dto.setToAccountId(id);
        dto.setIsDeleted(false);
        return friendRepository.findAll(getSpecification(dto));
    }

    private List<Friend> listFriendsToApprove(Long id) {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setToAccountId(getJwtUserIdFromSecurityContext());
        dto.setFromAccountId(id);
        dto.setIsDeleted(false);
        return friendRepository.findAll(getSpecification(dto));
    }

    private void createRecommendations() {
        log.info("FriendService in createRecommendations tried to create recommendations");

        List<Long> allIds = friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.FRIEND)))
                .stream()
                .map(Friend::getFromAccountId)
                .distinct()
                .collect(Collectors.toList());
        for (Long id : allIds) {
            List<Long> exceptIds = getFriendIdsWhoBlocked(id);
            exceptIds.addAll(getFriendIdsWhoBlockedMe(id));
            exceptIds.addAll(getRequestTo(id));
            exceptIds.addAll(getRequestFrom(id));
            //??
            exceptIds.addAll(alreadyFriends(id).stream().map(Friend::getToAccountId).collect(Collectors.toList()));
            exceptIds.addAll(alreadyFriends(id).stream().map(Friend::getFromAccountId).collect(Collectors.toList()));
            exceptIds.add(id);

            List<Long> friendIds = getFriendsIds(id);
            Map<Long, Long> friendsFriendsIds = new TreeMap<>();

            for (Long friendId : friendIds) {
                if (!exceptIds.contains(friendId)) {
                    List<Long> list = getFriendsIds(friendId);
                    for (Long f : list) {
                        if (friendIds.contains(f) || exceptIds.contains(f)) continue;
                        if (friendsFriendsIds.containsKey(f)) {
                            friendsFriendsIds.put(f, friendsFriendsIds.get(f) + 1L);
                        } else {
                            friendsFriendsIds.put(f, 1L);
                        }
                    }
                }
            }
            log.info("RECOMMENDATION MAP: " + friendsFriendsIds);

            List<Long> resultList = friendsFriendsIds.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(10)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            Long count = 0L;

            for (Long entry : resultList) {
                count++;

                friendRepository.deleteAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.RECOMMENDATION, entry))));

                Friend friend = friendMapper.friendToFriendTo(accountMapper
                        .convertToDto(accountRepository.findById(id).orElseThrow(EntityNotFoundResponseStatusException::new)));

                friend.setStatusCode(StatusCode.RECOMMENDATION);
                friend.setPreviousStatusCode(StatusCode.NONE);
                friend.setToAccountId(entry);
                friend.setRating(count);
                friend.setIsDeleted(false);
                friendRepository.save(friend);
            }
            log.info("RECOMMENDATIONS LIST: " + resultList);
        }
    }

    private List<Long> getFriendsIds(Long id) {
        FriendSearchDto mySearchDto = new FriendSearchDto();
        mySearchDto.setToAccountId(id);
        mySearchDto.setStatusCode(StatusCode.FRIEND);
        mySearchDto.setIsDeleted(false);
        return friendRepository.findAll(getSpecification(mySearchDto))
                .stream()
                .map(Friend::getFromAccountId).collect(Collectors.toList());
    }

    private List<Long> getFriendIdsWhoBlockedMe(Long id) {
        FriendSearchDto searchDto = new FriendSearchDto();
        searchDto.setToAccountId(id);
        searchDto.setStatusCode(StatusCode.BLOCKED);
        searchDto.setIsDeleted(false);
        return friendRepository.findAll(getSpecification(searchDto))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList());
    }

    public List<Long> getFriendIdsWhoBlocked(Long id) {
        FriendSearchDto searchDto = new FriendSearchDto();
        searchDto.setFromAccountId(id);
        searchDto.setStatusCode(StatusCode.BLOCKED);
        searchDto.setIsDeleted(false);
        return friendRepository.findAll(getSpecification(searchDto))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList());
    }

    private List<Long> getRequestTo(Long id) {
        List<Long> ids = friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_TO)))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList());

        ids.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.REQUEST_TO,id)))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList()));
        return ids;
    }

    private List<Long> getRequestFrom(Long id) {
        List<Long> ids = friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.REQUEST_FROM, id)))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList());

        ids.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id,StatusCode.REQUEST_FROM)))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList()));
        return ids;
    }

    private Specification<Friend> getSpecification(FriendSearchDto friendSearchDto) {
        return getBaseSpecification(friendSearchDto)
                .and(equal(Friend_.statusCode, friendSearchDto.getStatusCode(), true))
                .and(equal(Friend_.toAccountId, friendSearchDto.getToAccountId(), true))
                .and(equal(Friend_.fromAccountId, friendSearchDto.getFromAccountId(), true))
                .and(in(Friend_.id, friendSearchDto.getIds(), true));
    }
}




