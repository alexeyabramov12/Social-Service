package ru.skillbox.diplom.group33.social.service.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group33.social.service.config.socket.handler.NotificationHandler;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.dto.storage.StorageDto;
import ru.skillbox.diplom.group33.social.service.exception.EntityNotFoundResponseStatusException;
import ru.skillbox.diplom.group33.social.service.mapper.post.PostMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.Post_;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag_;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.service.friend.FriendService;
import ru.skillbox.diplom.group33.social.service.service.post.like.LikeService;
import ru.skillbox.diplom.group33.social.service.service.post.tag.TagService;
import ru.skillbox.diplom.group33.social.service.service.storage.StorageService;

import javax.persistence.criteria.Join;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.POST;
import static ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final NotificationHandler notificationHandler;
    private final PostRepository repository;
    private final StorageService storageService;
    private final PostMapper mapper;
    private final TagService tagService;
    private final LikeService likeService;
    private final FriendService friendService;
    private final AccountService accountService;

    public PostDto getById(Long id) {
        log.info("IN PostService getById - id: {}", id);
        return mapper.convertToDto(repository.findById(id).orElseThrow(EntityNotFoundResponseStatusException::new));
    }

    public Page<PostDto> getAll(PostSearchDto postSearchDto, Pageable page) {
        log.info("IN PostService getAll - postSearchDto: {}", postSearchDto);
        Long userId = getJwtUserIdFromSecurityContext();

        if (postSearchDto.getAuthor() != null) {
            AccountSearchDto accountSearchDto = new AccountSearchDto();
            String author = postSearchDto.getAuthor().trim();
            String[] arrayString = author.split(" ");
            if (arrayString.length == 1) {
                accountSearchDto.setFirstName(author);
                log.info("IN PostService getAll - search with first name - {}", author);
            } else if (arrayString.length == 2) {
                accountSearchDto.setFirstName(arrayString[0]);
                accountSearchDto.setLastName(arrayString[1]);
                log.info("IN PostService getAll - search with first name - {} and last name - {}", arrayString[0], arrayString[1]);
            } else return Page.empty(page);

            List<AccountDto> accountList = accountService.search(accountSearchDto, page).toList();
            List<Long> accountFriendIds = friendService.getFriendsIds(userId);

            List<Long> accountIds = new ArrayList<>();
            accountList.forEach(a -> {
                if (accountFriendIds.contains(a.getId())) {
                    accountIds.add(a.getId());
                }
            });

            postSearchDto.setAccountIds(accountIds);
        }

        List<Long> blockedIds = friendService.getFriendIdsWhoBlocked(userId);

        List<Long> accountIds = friendService.getFriendsIds(userId);
        accountIds.add(userId);

        postSearchDto.setAccountIds(postSearchDto.getAccountIds() == null ? accountIds : postSearchDto.getAccountIds());
        postSearchDto.setBlockedIds(blockedIds.size() == 0 ? null : blockedIds);
        postSearchDto.setDateFrom(postSearchDto.getDateFrom() == null ? ZonedDateTime.now().
                minusYears(10).toEpochSecond() : postSearchDto.getDateFrom());
        Page<Post> posts = repository.findAll(getSpecification(postSearchDto), page);

        return new PageImpl<>(posts.map(e -> {
            PostDto dto = mapper.convertToDto(e);
            dto.setMyLike(likeService.getMyLike(dto.getId(), LikeType.POST));
            return dto;
        }).toList(), page, posts.getTotalElements());
    }

    public PostDto create(PostDto dto) {
        log.info("IN PostService create - dto {}", dto);
        Post post = mapper.initEntity(dto);
        post.setTags(tagService.create(post, dto.getTags()));
        notificationHandler.sendNotificationReceivers(POST, post.getTitle());
        return mapper.convertToDto(repository.save(post));
    }

    public PostDto createWithPublishDate(Long publishDate, PostDto dto) {
        log.info("IN PostService createWithPublishDate - publishDate - {}, dto {}", publishDate, dto);

        dto.setPublishDate(ZonedDateTime.ofInstant(Instant.ofEpochSecond(publishDate), ZoneId.systemDefault()));
        Post post = mapper.initEntity(dto);

        post.setTags(tagService.create(post, dto.getTags()));
        notificationHandler.sendNotificationReceivers(POST, post.getTitle());
        return mapper.convertToDto(repository.save(post));
    }

    public PostDto update(Long id, PostDto dto) {
        log.info("IN PostService update - id: {} dto: {}", id, dto);
        Post post = repository.findById(id).orElseThrow(EntityNotFoundResponseStatusException::new);
        post.setTags(tagService.update(post, post.getTags(), dto.getTags()));
        return mapper.convertToDto(repository.save(mapper.updateEntity(dto, post)));
    }

    public void deleteById(Long id) {
        log.info("IN PostService deleteById - id: {}", id);
        repository.deleteById(id);
    }


    private static Specification<Post> getSpecification(PostSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(equal(Post_.isDeleted, false, true))
                .and(in(Post_.id, searchDto.getIds(), true))
                .and(in(Post_.authorId, searchDto.getAccountIds(), false))
                .and(notIn(Post_.authorId, searchDto.getBlockedIds(), true))
                .and(likeLowerCase(Post_.title, searchDto.getTitle(), true))
                .and(likeLowerCase(Post_.postText, searchDto.getPostText(), true))
                .and(containsTag(searchDto.getTags()))
                .and(between(Post_.publishDate,
                        ZonedDateTime.ofInstant(Instant.ofEpochSecond(searchDto.getDateFrom()), ZoneId.systemDefault()),
                        searchDto.getDateTo() == null ?
                                ZonedDateTime.now() :
                                ZonedDateTime.ofInstant(Instant.ofEpochSecond(searchDto.getDateTo()), ZoneId.systemDefault()), true));
    }

    private static Specification<Post> containsTag(Set<String> tags) {
        return (root, query, builder) -> {
            if (tags == null || tags.isEmpty()) {
                return builder.conjunction();
            }
            Join<Post, Tag> join = root.join(Post_.tags);
            return builder.in(join.get(Tag_.NAME)).value(tags);
        };
    }

    public StorageDto addPostPhoto(MultipartFile file) throws IOException {
        StorageDto storageResult = storageService.uploadFile(file);
        return storageResult;
    }

}
