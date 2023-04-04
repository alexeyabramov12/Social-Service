package ru.skillbox.diplom.group33.social.service.model.dialog;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.Message;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dialog")
public class Dialog extends BaseEntity {

    @Column(name = "user1_id", nullable = false)
    private Long userId1;
    @Column(name = "user2_id", nullable = false)
    private Long userId2;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message_id", referencedColumnName = "id")
    private Message lastMessage;
}
