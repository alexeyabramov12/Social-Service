package ru.skillbox.diplom.group33.social.service.model.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "city", nullable = false)
    @OneToMany(mappedBy = "countryId", fetch = FetchType.LAZY)
    private List<City> city;


}
