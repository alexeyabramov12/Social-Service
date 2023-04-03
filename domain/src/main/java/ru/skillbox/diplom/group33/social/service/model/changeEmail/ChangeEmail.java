package ru.skillbox.diplom.group33.social.service.model.changeEmail;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "change_email")
public class ChangeEmail  {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "temp")
    private UUID temp;
    private String email;
    @Column(name = "new_email")
    private String newEmail;
    private String code;
}
