package ru.skillbox.diplom.group33.social.service.model.friend;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "friend")
public class Friend extends BaseEntity {
    private String photo;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String city;
    private String country;
    @Column(name = "birthday", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime birthDay;
    @Column(name = "is_online")
    private Boolean isOnline;
    @Column(name = "from_account_id")
    private Long fromAccountId;
    @Column(name = "status_code")
    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;
    @Column(name = "previous_status_code")
    @Enumerated(EnumType.STRING)
    private StatusCode previousStatusCode;
    @Column(name = "to_account_id")
    private Long toAccountId;
    private Long rating;

    public Friend(Long fromAccountId, StatusCode statusCode, Long toAccountId) {
        setIsDeleted(false);
        this.fromAccountId = fromAccountId;
        this.statusCode = statusCode;
        this.toAccountId = toAccountId;
    }

    public Friend(Long fromAccountId, StatusCode statusCode, Long toAccountId, StatusCode previousStatusCode) {
        setIsDeleted(false);
        this.fromAccountId = fromAccountId;
        this.statusCode = statusCode;
        this.toAccountId = toAccountId;
        this.previousStatusCode = previousStatusCode;
    }
}
