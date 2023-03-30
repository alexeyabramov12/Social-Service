package ru.skillbox.diplom.group33.social.service.service.post.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentType;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.mapper.post.like.LikeMapper;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment;
import ru.skillbox.diplom.group33.social.service.model.post.like.Like;
import ru.skillbox.diplom.group33.social.service.repository.post.PostRepository;
import ru.skillbox.diplom.group33.social.service.repository.post.comment.CommentRepository;
import ru.skillbox.diplom.group33.social.service.repository.post.like.LikeRepository;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/TestData.sql")
@ExtendWith(MockitoExtension.class)
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
class LikeServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private LikeMapper likeMapper;

    @Mock
    private SecurityContext securityContext;


    private Long COMMENT_ID = 2L;
    private Long POST_ID = 1L;
    private Long AUTHOR_ID = 1L;

    private Long ITEM_ID = 1L;

    Long itemId = 1L;


    @Mock
    JwtUser jwtUser = new JwtUser(1L, "Джон", "Сильвер", "test@test.ru"
            , "test", null);


    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwtUser);
        Mockito.lenient().when(SecurityUtils.getJwtUserIdFromSecurityContext()).thenReturn(1L);

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testChangePostLike() {
        Long itemId = 1L;
        Post post = new Post();
        post.setId(itemId);
        post.setLikeAmount(0);
        post.setTimeChanged(ZonedDateTime.now(TimeZone.getTimeZone("Europe/Madrid").toZoneId()));
        post.setAuthorId(1L);
        post.setTitle("оке");
        post.setPostText("her");
        post.setCommentsCount(0);
        postRepository.save(post);
        LikeType likeType = LikeType.POST;
        LikeService likeService = new LikeService(postRepository, commentRepository, likeRepository, likeMapper);
        Like like = new Like();
        like.setAuthorId(SecurityUtils.getJwtUserIdFromSecurityContext());
        like.setType(likeType);
        like.setItemId(itemId);
        like.setIsDeleted(false);
        like.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(likeMapper.initLikeDto(new LikeDto(), likeType, itemId)).thenReturn(like);
        likeRepository.save(like);
        likeService.changePostLike(like.getItemId(), like.getType());


        LikeDto expectedLikeDto = new LikeDto();
        expectedLikeDto.setId(1L);
        expectedLikeDto.setType(like.getType());
        expectedLikeDto.setItemId(like.getItemId());
        expectedLikeDto.setIsDeleted(like.getIsDeleted());
        expectedLikeDto.setAuthorId(like.getAuthorId());
        expectedLikeDto.setTime(like.getTime());

        LikeDto actualLikeDto = new LikeDto();
        actualLikeDto.setId(1L);
        actualLikeDto.setAuthorId(1L);
        actualLikeDto.setItemId(1L);
        actualLikeDto.setType(LikeType.POST);
        actualLikeDto.setTime(null);


        assertEquals(expectedLikeDto, actualLikeDto);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testChangeLikeAmountAddLike() {
        LikeService likeService = new LikeService(postRepository, commentRepository, likeRepository, likeMapper);

        Like mockLike = new Like();
        mockLike.setId(1L);
        mockLike.setIsDeleted(false);
        mockLike.setItemId(1L);
        mockLike.setType(LikeType.POST);

        Post mockPost = new Post();
        mockPost.setId(POST_ID);
        mockPost.setLikeAmount(1);
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        likeService.changeLikeAmount(mockLike.getItemId(), mockLike.getType(), 1);

        // Проверка:
        assertEquals(Optional.of(2), Optional.of(mockPost.getLikeAmount()));

    }


    @Test
    void testChangeCommentLike() {
        Long itemId = 1L;
        LikeType likeType = LikeType.COMMENT;

        Like like = new Like();
        like.setAuthorId(SecurityUtils.getJwtUserIdFromSecurityContext());
        like.setType(likeType);
        like.setItemId(itemId);
        like.setIsDeleted(false);
        LikeService likeService = new LikeService(postRepository, commentRepository, likeRepository, likeMapper);


        Comment comment = new Comment();
        comment.setId(itemId);
        comment.setMyLike(true);
        comment.setLikeAmount(0);
        comment.setAuthorId(1L);
        comment.setCommentType(CommentType.COMMENT);
        when(commentRepository.findById(itemId)).thenReturn(Optional.of(comment));
        when(likeMapper.initLikeDto(new LikeDto(), likeType, itemId)).thenReturn(like);
        likeRepository.save(like);

        LikeDto expectedLikeDto = new LikeDto();
        expectedLikeDto.setId(like.getId());
        expectedLikeDto.setType(like.getType());
        expectedLikeDto.setItemId(like.getItemId());
        expectedLikeDto.setIsDeleted(like.getIsDeleted());
        expectedLikeDto.setAuthorId(like.getAuthorId());
        expectedLikeDto.setTime(like.getTime());
        likeService.changeCommentLike(like.getItemId(), like.getType());


        LikeDto actualLikeDto = new LikeDto();
        actualLikeDto.setAuthorId(like.getAuthorId());
        actualLikeDto.setId(like.getId());
        actualLikeDto.setType(like.getType());
        actualLikeDto.setItemId(like.getItemId());

        assertEquals(expectedLikeDto, actualLikeDto);
    }

}

