package ru.skillbox.diplom.group33.social.service.config;

import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;
import ru.skillbox.diplom.group33.social.service.dto.post.PostDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentDto;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentType;
import ru.skillbox.diplom.group33.social.service.model.captcha.Captcha;
import ru.skillbox.diplom.group33.social.service.model.notification.Notification;

import java.time.ZonedDateTime;

import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.COMMENT_COMMENT;

public class SourceDataFactory {

    public static final String TEST_EMAIL =  "ex@exemple.ru";
    public static final String TEST_NAME = "test";
    public static final String UPDATE_TEST_NAME = "test_update";
    public static final String TEST_PASSWORD = "password";

    public static AuthenticateDto authenticateDto() {
        AuthenticateDto authDto = new AuthenticateDto();
        authDto.setEmail(TEST_EMAIL);
        authDto.setPassword(TEST_PASSWORD);

        return authDto;
    }

    public static RegistrationDto registrationDto(Captcha captcha, CaptchaDto captchaDto) {
        RegistrationDto registration = new RegistrationDto();
        registration.setEmail(TEST_EMAIL);
        registration.setFirstName(TEST_NAME);
        registration.setLastName(TEST_NAME);
        registration.setPassword1(TEST_PASSWORD);
        registration.setPassword2(TEST_PASSWORD);
        registration.setToken(captchaDto.getSecret());
        registration.setCode(captcha.getCode());

        return registration;
    }

    public static SettingsRequest settingsRequest() {
        return new SettingsRequest(false, NotificationType.POST);
    }

    public static Notification notification(Long id) {
        Notification notification = new Notification();
        notification.setIsDeleted(false);
        notification.setNotificationType(COMMENT_COMMENT);
        notification.setContent("");
        notification.setAuthorId(id);
        notification.setReceiverId(id);
        notification.setTime(ZonedDateTime.now());
        return notification;
    }

    public static PostDto postDto() {
        PostDto postDto = new PostDto();
        postDto.setPostText("Test text");
        postDto.setImagePath("");
        postDto.setTitle("Title");
        return postDto;
    }

    public static CommentDto commentDto(Long postId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText("Comment");
        commentDto.setTime(ZonedDateTime.now());
        commentDto.setCommentType(CommentType.POST);
        commentDto.setPostId(postId);
        return commentDto;
    }
}
