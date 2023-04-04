package ru.skillbox.diplom.group33.social.service.mapper.post.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeDto;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.model.post.like.Like;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class LikeMapperTest {

    private LikeMapper mapper;

    @Mock
    private Authentication authentication;

    private SecurityContextHolder securityContextHolder;


    @BeforeEach
    public void setup() {
        mapper = Mappers.getMapper(LikeMapper.class);

        JwtUser jwtUser = new JwtUser(1L, "Джон", "Сильвер", "test@test.ru"
                , "test", null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Test
    public void convertToDto() {
        // given
        Like like = new Like();
        like.setId(1L);

        // when
        LikeDto likeDto = mapper.convertToDto(like);

        // then
        assertNotNull(likeDto);
        assertEquals(like.getId(), likeDto.getId());
        assertEquals(false, likeDto.getIsDeleted());
    }

    @Test
    public void convertToEntity() {
        // given
        LikeDto likeDto = new LikeDto();
        likeDto.setId(1L);

        // when
        Like like = mapper.convertToEntity(likeDto);

        // then
        assertNotNull(like);
        assertEquals(likeDto.getId(), like.getId());
        assertEquals(false, like.getIsDeleted());
    }

    @Test
    public void initLikeDto() {
        // given
        LikeDto likeDto = new LikeDto();
        likeDto.setType(LikeType.POST);
        likeDto.setItemId(1L);

        SecurityUtils securityUtils = mock(SecurityUtils.class);

        LikeMapper mapper = Mappers.getMapper(LikeMapper.class);
        mapper = new LikeMapperImpl();

        // when
        Like like = mapper.initLikeDto(likeDto, LikeType.POST, 1L);

        // then
        assertNotNull(like);
        assertNotNull(like.getTime());
        assertEquals(likeDto.getType(), like.getType());
        assertEquals(likeDto.getItemId(), like.getItemId());
        assertEquals(false, like.getIsDeleted());
    }
}
