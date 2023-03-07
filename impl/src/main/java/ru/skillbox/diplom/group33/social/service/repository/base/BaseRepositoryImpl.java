package ru.skillbox.diplom.group33.social.service.repository.base;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@NoRepositoryBean
public class BaseRepositoryImpl<E extends BaseEntity> extends SimpleJpaRepository<E, Long> implements BaseRepository<E> {

    EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<E, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.findById(id).ifPresent(x -> {
            x.setIsDeleted(true);
            super.save(x);
        });
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends E> entities) {
        entities.forEach(x -> {
            x.setIsDeleted(true);
            super.save(x);
        });
    }

    @Override
    @Transactional
    public void delete(E entity) {
        entity.setIsDeleted(true);
        super.save(entity);
    }
}
