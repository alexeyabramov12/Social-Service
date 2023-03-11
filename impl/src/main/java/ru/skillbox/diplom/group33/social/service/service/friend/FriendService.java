package ru.skillbox.diplom.group33.social.service.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.RecommendationFriendsDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;
import ru.skillbox.diplom.group33.social.service.exception.EntityNotFoundResponseStatusException;
import ru.skillbox.diplom.group33.social.service.mapper.friend.FriendMapper;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend_;
import ru.skillbox.diplom.group33.social.service.repository.friend.FriendRepository;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.util.*;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class
FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;

    private final AccountService accountService;


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
        friend.setPreviousStatusCode(friend.getStatusCode());
        friend.setStatusCode(StatusCode.NONE);
        log.info("FriendService: delete by id {}", id);
        friendRepository.save(friend);
    }

    public List<RecommendationFriendsDto> getRecommendations() {
        log.info("FriendService: getRecommendations");
        createRecommendations();

        FriendSearchDto friendSearchDto = new FriendSearchDto();
        friendSearchDto.setToAccountId(SecurityUtils.getJwtUserIdFromSecurityContext());
        friendSearchDto.setStatusCode(StatusCode.RECOMMENDATION);

        return friendRepository.
                findAll(getSpecification(friendSearchDto))
                .stream().map(friendMapper::convertToRecommendation).collect(Collectors.toList());
    }

    public FriendDto blockFriend(Long id) {
        log.info("FriendService: block by id {}", id);
        Friend friend = friendRepository.findById(id).orElseThrow(EntityNotFoundResponseStatusException::new);
        if (friend.getStatusCode() == StatusCode.BLOCKED) {
            friend.setStatusCode(friend.getPreviousStatusCode());
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
        Friend me = friendRepository.findById(SecurityUtils.getJwtUserIdFromSecurityContext())
                .orElseThrow(EntityNotFoundResponseStatusException::new);
        me.setPreviousStatusCode(me.getStatusCode());
        me.setStatusCode(StatusCode.FRIEND);

        Friend friend = friendRepository.findById(id)
                .orElseThrow(EntityNotFoundResponseStatusException::new);
        friend.setPreviousStatusCode(friend.getStatusCode());
        friend.setStatusCode(StatusCode.FRIEND);
        friendRepository.save(friend);
        friendRepository.save(me);
        return friendMapper.convertToDto(friend);
    }


    public FriendDto addFriend(Long id) {
        log.info("FriendService: add by id {}", id);
//        List<Friend> friendsToAdd = getCurrentInvoicesByAccountId(id);
//        if (friendsToAdd.isEmpty()) {
//            return createInvoices(id);
//        } else {
//            for (Friend friend : friendsToAdd) {
//                if (friend.getStatusCode().equals(StatusCode.RECOMMENDATION)) {
//                    friendRepository.delete(friend);
//                    createInvoices(id);
//                }
//            }
//        }
//        return friendMapper.convertToDtoList(friendsToAdd);

        AccountDto myAccount = accountService.getAccount();
        AccountDto friendAccount = accountService.getById(id);

        Friend friendFrom = friendMapper.friendToFriendFrom(myAccount);
        friendFrom.setToAccountId(id);
        Friend friendTo = friendMapper.friendToFriendTo(friendAccount);

        friendRepository.save(friendTo);
        friendRepository.save(friendFrom);

        return friendMapper.convertToDto(friendTo);
    }

    private List<FriendDto> createInvoices(Long id) {
        Long myId = SecurityUtils.getJwtUserIdFromSecurityContext();

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
        Long myId = SecurityUtils.getJwtUserIdFromSecurityContext();

        List<Friend> listFriend = friendRepository.findAll(getSpecification(new FriendSearchDto(myId, StatusCode.REQUEST_TO, id)));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_TO, myId))));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(myId, StatusCode.REQUEST_FROM, id))));
        listFriend.addAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.REQUEST_FROM, myId))));
        return listFriend;
    }

    private List<Friend> getCurrentInvoicesByAccountId(Long id) {
        List<Friend> list = listFriendsToAdd(id);
        list.addAll(listFriendsToApprove(id));
        return list;
    }

    private List<Friend> listFriendsToAdd(Long id) {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setFromAccountId(SecurityUtils.getJwtUserIdFromSecurityContext());
        dto.setToAccountId(id);

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
        friendList.addAll(alreadySendRequests(id));
        friendList.addAll(listFriendsToApprove(id));
        return friendList;
    }

    private void createRecommendations() {
        log.info("FriendService in createRecommendations tried to create recommendations");
        FriendSearchDto search = new FriendSearchDto();
        search.setStatusCode(StatusCode.FRIEND);
        List<Long> allIds = friendRepository.findAll(getSpecification(search))
                .stream()
                .map(Friend::getFromAccountId)
                .toList();
        for (Long id : allIds) {
            //id которые исключаются из выборки
            List<Long> exceptIds = getFriendIdsWhoBlocked(id);
            exceptIds.addAll(getFriendIdsWhoBlockedMe(id));
            exceptIds.addAll(getFriendIdsWhoBlockedMe(id));
            exceptIds.addAll(getRequestTo(id));
            exceptIds.addAll(getRequestFrom(id));
            exceptIds.add(id);
            //друзья текущего id
            List<Long> friendIds = getFriendsIds(id);
            Map<Long, Long> friendsFriendsIds = new TreeMap<>();

            for (Long friendId : friendIds) {
                if (!exceptIds.contains(friendId)) {
                    //друзья друзей текущего id
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
                    .toList();

            Long count = 0L;

            for (Long entry : resultList) {
                count++;

                friendRepository.deleteAll(friendRepository.findAll(getSpecification(new FriendSearchDto(id, StatusCode.RECOMMENDATION, entry))));

                Friend friend = new Friend();
                friend.setFromAccountId(id);
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
        return friendRepository.findAll(getSpecification(mySearchDto))
                .stream()
                .map(Friend::getFromAccountId).toList();
    }

    private List<Long> getFriendIdsWhoBlockedMe(Long id) {
        FriendSearchDto searchDto = new FriendSearchDto();
        searchDto.setToAccountId(id);
        searchDto.setStatusCode(StatusCode.BLOCKED);
        return friendRepository.findAll(getSpecification(searchDto))
                .stream()
                .map(Friend::getFromAccountId)
                .collect(Collectors.toList());
    }

    private List<Long> getFriendIdsWhoBlocked(Long id) {
        FriendSearchDto searchDto = new FriendSearchDto();
        searchDto.setFromAccountId(id);
        searchDto.setStatusCode(StatusCode.BLOCKED);
        return friendRepository.findAll(getSpecification(searchDto))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList());
    }

    private List<Long> getRequestTo(Long id) {
        FriendSearchDto searchDto = new FriendSearchDto();
        searchDto.setFromAccountId(id);
        searchDto.setStatusCode(StatusCode.REQUEST_TO);
        return friendRepository.findAll(getSpecification(searchDto))
                .stream()
                .map(Friend::getToAccountId)
                .collect(Collectors.toList());
    }


    private List<Long> getRequestFrom(Long id) {
        FriendSearchDto searchDto = new FriendSearchDto();
        searchDto.setToAccountId(id);
        searchDto.setStatusCode(StatusCode.REQUEST_FROM);
        return friendRepository.findAll(getSpecification(searchDto)).stream().map(Friend::getFromAccountId).collect(Collectors.toList());
    }

    private Specification<Friend> getSpecification(FriendSearchDto friendSearchDto) {
        return getBaseSpecification(friendSearchDto)
                .and(equal(Friend_.statusCode, friendSearchDto.getStatusCode(), true))
                .and(equal(Friend_.toAccountId, friendSearchDto.getToAccountId(), true))
                .and(equal(Friend_.fromAccountId, friendSearchDto.getFromAccountId(), true))
                .and(in(Friend_.id, friendSearchDto.getIds(), true));
    }
}
