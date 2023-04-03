package ru.skillbox.diplom.group33.social.service.mapper.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.PostType;
import ru.skillbox.diplom.group33.social.service.model.post.Post;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostMapperTest {

    private PostMapper postMapper;

    @BeforeEach
    public void setup() {
        postMapper = Mappers.getMapper(PostMapper.class);
        JwtUser jwtUser = new JwtUser(1L, "Джон", "Сильвер", "test@test.ru"
                , "test", null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testConvertToDto() {
        //given
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));
        post.setTags(tags);

        //when
        PostDto postDto = postMapper.convertToDto(post);

        //then
        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(1, postDto.getTags().size());
    }

    @Test
    public void testConvertToEntity() {
        //given
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setLikeAmount(2);
        postDto.setId(1L);

        //when
        Post post = postMapper.convertToEntity(postDto);

        //then
        assertEquals(postDto.getTitle().trim(), post.getTitle());
        assertEquals(2, post.getLikeAmount());
        assertEquals("Test Title", post.getTitle());
        assertEquals(1L, post.getId());

    }

    @Test
    public void testInitEntity() {
        //given
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setPostText("Test post text");
        postDto.setPublishDate(ZonedDateTime.now().minusDays(1));
        Set<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        postDto.setTags(tags);

        //when
        Post post = postMapper.initEntity(postDto);

        //then
        assertNull(post.getId());
        assertEquals(postDto.getTitle().trim(), post.getTitle());
        assertEquals(postDto.getPostText().trim(), post.getPostText());
        assertEquals(postDto.getPublishDate(), post.getPublishDate());
        assertEquals(SecurityUtils.getJwtUserIdFromSecurityContext(), post.getAuthorId());
        assertEquals(PostType.POSTED, post.getPostType());
        assertFalse(post.getIsDeleted());
        assertFalse(post.getIsBlocked());
        assertEquals(0, post.getLikeAmount());
        assertEquals(0, post.getCommentsCount());
        assertFalse(post.getMyLike());

    }

    @Test
    public void testUpdateEntity() {
        //given
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Old Title");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag1"));
        post.setTags(tags);

        PostDto postDto = new PostDto();
        postDto.setTitle("New Title");
        postDto.setPostText("New post text");
        postDto.setTags(new HashSet<>(Arrays.asList("tag1", "tag2")));

        //when
        Post updatedPost = postMapper.updateEntity(postDto, post);

        //then
        assertEquals(post.getId(), updatedPost.getId());
        assertNotEquals(post.getTitle(), updatedPost.getTitle());
        assertEquals(postDto.getTitle().trim(), updatedPost.getTitle());
        assertEquals(postDto.getPostText().trim(), updatedPost.getPostText());
        assertEquals(post.getAuthorId(), updatedPost.getAuthorId());
        assertEquals(post.getPublishDate(), updatedPost.getPublishDate());
        assertEquals(post.getPostType(), updatedPost.getPostType());
        assertEquals(post.getIsDeleted(), updatedPost.getIsDeleted());
        assertEquals(post.getIsBlocked(), updatedPost.getIsBlocked());
        assertEquals(post.getLikeAmount(), updatedPost.getLikeAmount());
        assertEquals(post.getCommentsCount(), updatedPost.getCommentsCount());
        assertEquals(post.getMyLike(), updatedPost.getMyLike());
        assertEquals(post.getImagePath(), updatedPost.getImagePath());
        assertEquals(1, updatedPost.getTags().size());

    }
}
