package ru.skillbox.diplom.group33.social.service.model.passwordRecovery;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "password_recovery")
public class PasswordRecovery {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "secret_link_id")
    private UUID secretLinkId;
    private String email;
}
