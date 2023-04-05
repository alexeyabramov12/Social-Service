package ru.skillbox.diplom.group33.social.service.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.socket.handler.NotificationHandler;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;
import ru.skillbox.diplom.group33.social.service.exception.EntityNotFoundResponseStatusException;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.mapper.friend.FriendMapper;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend_;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.repository.friend.FriendRepository;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.FRIEND_REQUEST;
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
    private final NotificationHandler notificationHandler;

    public FriendDto getById(Long id) {

        log.info("FriendService: getById {}", id);
        return friendMapper.convertToDto(friendRepository.findById(id)
                .orElseThrow(EntityNotFoundResponseStatusException::new));
    }

    public Page<FriendDto> getAll(FriendSearchDto searchDto, Pageable page) {
        log.info("FriendService: getAll");

        searchDto.setFromAccountId(getJwtUserIdFromSecurityContext());
        searchDto.setIsDeleted(false);

        List<Friend> friendList = friendRepository.findAll(getSpecification(searchDto));
        List<FriendDto> friendsToShow = new ArrayList<>();

        if (searchDto.getStatusCode() == null) {
            searchDto.setStatusCode(StatusCode.NONE);
        }

        List<Friend> toUpdate = friendRepository
                .findAll(getSpecification(new FriendSearchDto
                        (getJwtUserIdFromSecurityContext(), StatusCode.FRIEND)));
        if (!toUpdate.isEmpty()) {
            for (Friend friend : toUpdate) {
                Account updated = accountRepository.getReferenceById(friend.getToAccountId());
                friend.setPhoto(updated.getPhoto());
                friend.setFirstName(updated.getFirstName());
                friend.setLastName(updated.getLastName());
                friend.setCity(updated.getCity());
                friend.setCountry(updated.getCountry());
                friend.setBirthDay(updated.getBirthDate());
                friendRepository.save(friend);
            }
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
        return (long) friendRepository
                .findAll(getSpecification(new FriendSearchDto
                        (StatusCode.REQUEST_TO, getJwtUserIdFromSecurityContext())))
                .size();
    }

    public void delete(Long id) {
        log.info("FriendService: delete by id {}", id);
        List<Friend> invoices = getCurrentInvoicesByAccountId(id);
        for (Friend friend : invoices) {
            if (friend.getStatusCode() == StatusCode.FRIEND) {
                if (friend.getToAccountId().equals(id)) {
                    friend.setStatusCode(StatusCode.SUBSCRIBED);
                } else {
                    friend.setStatusCode(StatusCode.NONE);
                }
                friend.setPreviousStatusCode(StatusCode.FRIEND);
                friendRepository.save(friend);
            } else {
                friend.setPreviousStatusCode(friend.getStatusCode());
                friendRepository.delete(friend);
            }
        }
    }

    public List<FriendDto> getRecommendations() {
        log.info("FriendService: getRecommendations");
        createRecommendations();

        return friendRepository.
                findAll(getSpecification(new FriendSearchDto
                        (StatusCode.RECOMMENDATION, getJwtUserIdFromSecurityContext())))
                .stream().map(friendMapper::convertToDto).collect(Collectors.toList());
    }

    public void blockFriend(Long id) {
        log.info("FriendService: block by id {}", id);

        List<Friend> invoices = getCurrentInvoicesByAccountId(id);
        List<Friend> blocked = getBlockedFriends(id);
        Friend friend;
        if (invoices.isEmpty()) {
            friend = friendMapper.accountToFriend(accountRepository
                    .findById(id).orElse(null));
            friendRepository.save(friendMapper.friendToBlockedFriend(friend));
        } else {
            friend = friendRepository
                    .findAll(getSpecification(new FriendSearchDto(SecurityUtils.getJwtUserIdFromSecurityContext(), id)))
                    .get(0);
            if (blocked.isEmpty()) {
                friendRepository.deleteAll(invoices);
                friend.setStatusCode(StatusCode.BLOCKED);
                friend.setIsDeleted(false);
                friendRepository.save(friend);
            } else {
                friendRepository.deleteAll(blocked);
            }
        }
    }

    private List<Friend> getBlockedFriends(Long id) {
        return friendRepository
                .findAll(getSpecification(
                        new FriendSearchDto(getJwtUserIdFromSecurityContext(), StatusCode.BLOCKED, id)));
    }

    public List<FriendDto> approveFriend(Long id) {
        log.info("FriendService: approve by id {}", id);
        return createApproves(id);
    }

    private List<FriendDto> createApproves(Long id) {
        List<Friend> invoices = getCurrentInvoicesByAccountId(id);

        List<Friend> approved = new ArrayList<>();
        for (Friend friend : invoices) {
            if (friend.getStatusCode() == StatusCode.REQUEST_TO || friend.getStatusCode() == StatusCode.REQUEST_FROM) {
                approved.add(friendMapper.friendToApprovedFriend(friend));
            } else {
                friend.setIsDeleted(true);
                approved.add(friend);
            }
        }
        friendRepository.saveAll(approved);
        return friendMapper.convertToDtoList(approved);
    }
    private List<Friend> alreadySendRequests(Long id) {

        List<Friend> listFriend = friendRepository
                .findAll(getSpecification(new FriendSearchDto
                        (getJwtUserIdFromSecurityContext(), StatusCode.REQUEST_TO, id)));
        listFriend.addAll(friendRepository
                .findAll(getSpecification(new FriendSearchDto
                        (id, StatusCode.REQUEST_TO, getJwtUserIdFromSecurityContext()))));
        listFriend.addAll(friendRepository
                .findAll(getSpecification(new FriendSearchDto
                        (getJwtUserIdFromSecurityContext(), StatusCode.REQUEST_FROM, id))));
        listFriend.addAll(friendRepository
                .findAll(getSpecification(new FriendSearchDto
                        (id, StatusCode.REQUEST_FROM, getJwtUserIdFromSecurityContext()))));
        return listFriend.stream().distinct().collect(Collectors.toList());
    }


    public List<FriendDto> addFriend(Long id) {
        log.info("FriendService: add by id {}", id);

        if(!alreadySendRequests(id).isEmpty()){
            return friendMapper.convertToDtoList(alreadySendRequests(id));
        }

        Friend friend = friendRepository.findById(id).orElse(null);
        if (friend != null && friend.getStatusCode() == StatusCode.RECOMMENDATION) {
            Friend friend1 = friendRepository
                    .findAll(getSpecification(new FriendSearchDto(friend.getToAccountId(), StatusCode.RECOMMENDATION,
                            friend.getFromAccountId()))).get(0);
            friend.setStatusCode(StatusCode.REQUEST_FROM);
            friend.setIsDeleted(false);
            friend1.setStatusCode(StatusCode.REQUEST_TO);

            List<Friend> listFriend = new ArrayList<>();
            listFriend.add(friend);
            listFriend.add(friend1);

            friendRepository.saveAll(listFriend);
            notificationHandler.sendNotification(friend.getFromAccountId(), getJwtUserIdFromSecurityContext(),
                    FRIEND_REQUEST, "У вас новая заявка в друзья!");

            return friendMapper.convertToDtoList(listFriend);
        }
        notificationHandler.sendNotification(id, getJwtUserIdFromSecurityContext(),
                FRIEND_REQUEST, "У вас новая заявка в друзья!");
        return createInvoices(id);
    }

    private List<Friend> getCurrentInvoicesByAccountId(Long id) {
        List<Friend> list = listFriendsToAdd(id);
        list.addAll(listFriendsToApprove(id));
        return list;
    }

    private List<FriendDto> createInvoices(Long id) {
        Long myId = getJwtUserIdFromSecurityContext();

        Friend friend = friendMapper.accountToFriendRequestTo(accountRepository.findById(myId).orElse(null));
        friend.setFromAccountId(id);
        Friend friend1 = friendMapper.accountToFriendRequestFrom(accountRepository.findById(id).orElse(null));

        List<Friend> listFriend = new ArrayList<>();

        listFriend.add(friend);
        listFriend.add(friend1);
        friendRepository.saveAll(listFriend);
        return friendMapper.convertToDtoList(listFriend);
    }

    private List<Friend> listFriendsToAdd(Long id) {
        return friendRepository.findAll(getSpecification(new FriendSearchDto(getJwtUserIdFromSecurityContext(), id)));
    }

    private List<Friend> listFriendsToApprove(Long id) {
        return friendRepository.findAll(getSpecification(new FriendSearchDto(id, getJwtUserIdFromSecurityContext())));
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

                friendRepository.deleteAll(friendRepository
                        .findAll(getSpecification(new FriendSearchDto(id, StatusCode.RECOMMENDATION, entry))));

                Friend friend = friendMapper.friendToFriendTo(accountMapper
                        .convertToDto(accountRepository.findById(id)
                                .orElseThrow(EntityNotFoundResponseStatusException::new)));

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

    public List<Long> getFriendsIds(Long id) {
        return friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.FRIEND, id)))
                .stream()
                .map(Friend::getFromAccountId).collect(Collectors.toList());
    }

    public List<Long> getFriendIdsWhoBlockedMe(Long id) {
        return friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.BLOCKED, id)))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList());
    }

    public List<Long> getFriendIdsWhoBlocked(Long id) {
        return friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.BLOCKED)))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList());
    }

    private List<Long> getRequestTo(Long id) {
        List<Long> ids = friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_TO)))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList());

        ids.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.REQUEST_TO, id)))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList()));
        return ids.stream().distinct().collect(Collectors.toList());
    }

    private List<Long> getRequestFrom(Long id) {
        List<Long> ids = friendRepository.findAll(getSpecification(new FriendSearchDto(StatusCode.REQUEST_FROM, id)))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList());

        ids.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_FROM)))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList()));
        return ids.stream().distinct().collect(Collectors.toList());
    }

    private Specification<Friend> getSpecification(FriendSearchDto friendSearchDto) {
        return getBaseSpecification(friendSearchDto)
                .and(equal(Friend_.statusCode, friendSearchDto.getStatusCode(), true))
                .and(equal(Friend_.toAccountId, friendSearchDto.getToAccountId(), true))
                .and(equal(Friend_.fromAccountId, friendSearchDto.getFromAccountId(), true))
                .and(likeLowerCase(Friend_.firstName, friendSearchDto.getFirstName(), true))
                .and(equal(Friend_.city, friendSearchDto.getCity(), true))
                .and(equal(Friend_.country, friendSearchDto.getCountry(), true))
                .and(between(Friend_.birthDay,
                        friendSearchDto.getAgeTo() == null ? null : ZonedDateTime.now().minusYears(friendSearchDto.getAgeTo()),
                        friendSearchDto.getAgeFrom() == null ? null : ZonedDateTime.now().minusYears(friendSearchDto.getAgeFrom()),
                        true))
                .and(in(Friend_.id, friendSearchDto.getIds(), true));
    }
}




