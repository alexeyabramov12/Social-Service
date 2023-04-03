package ru.skillbox.diplom.group33.social.service.mapper.post.comment;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.model.post.comment.Comment;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @BeforeEach
    public void setup() {
        JwtUser jwtUser = new JwtUser(1L, "Джон", "Сильвер", "test@test.ru"
                , "test", null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testConvertToDto() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setCommentText("hello world");
        comment.setTime(ZonedDateTime.now());

        CommentDto commentDto = commentMapper.convertToDto(comment);

        assertNotNull(commentDto);
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getCommentText(), commentDto.getCommentText());
        assertEquals(comment.getTime().toInstant().atOffset(ZoneOffset.UTC), commentDto.getTime().toInstant().atOffset(ZoneOffset.UTC));
    }

    @Test
    public void testConvertToEntity() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setCommentText("hello world");
        commentDto.setTime(ZonedDateTime.now());

        Comment comment = commentMapper.convertToEntity(commentDto);

        assertNotNull(comment);
        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getCommentText(), comment.getCommentText());
        assertEquals(commentDto.getTime().toInstant().atOffset(ZoneOffset.UTC), comment.getTime().toInstant().atOffset(ZoneOffset.UTC));
    }

    @Test
    public void testInitEntity() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setCommentText("hello world");

        Comment comment = commentMapper.initEntity(commentDto);

        assertNotNull(comment);
        assertEquals(commentDto.getCommentText().trim(), comment.getCommentText());
        assertEquals(false, comment.getIsDeleted());
        assertEquals(false, comment.getIsBlocked());
        assertEquals(0, comment.getLikeAmount());
        assertEquals(false, comment.getMyLike());
        assertEquals(0, comment.getCommentsCount());
        assertNotNull(comment.getTime());
        assertNotNull(comment.getTimeChanged());
        assertNotNull(comment.getAuthorId());
    }

}
