package ru.skillbox.diplom.group33.social.service.model.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_settings")
public class NotificationSettings extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;
    private Boolean post = true;
    @Column(name = "post_comment")
    private Boolean postComment = true;
    @Column(name = "comment_comment")
    private Boolean commentComment = true;
    private Boolean message = true;
    @Column(name = "friend_request")
    private Boolean friendRequest = true;
    @Column(name = "friend_birthday")
    private Boolean friendBirthday = true;
    @Column(name = "send_email_message")
    private Boolean sendEmailMessage = false;
}
