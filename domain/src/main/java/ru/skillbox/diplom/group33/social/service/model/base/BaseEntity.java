package ru.skillbox.diplom.group33.social.service.model.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 100)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}