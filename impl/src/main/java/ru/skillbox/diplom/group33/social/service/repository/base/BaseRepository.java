package ru.skillbox.diplom.group33.social.service.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    void deleteById(Long id);

    void deleteAll(Iterable<? extends E> entities);

    void delete(E entity);
}