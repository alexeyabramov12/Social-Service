package ru.skillbox.diplom.group33.social.service.utils.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.function.Supplier;

public class SpecificationUtils<T> {
    public static final Specification EMPTY_SPECIFICATION = (root, query, criteriaBuilder) -> {
        return null;
    };

    public static Specification getBaseSpecification(BaseSearchDto searchDto) {
        return equal(BaseEntity_.id, searchDto.getId(), true)
                .and(equal(BaseEntity_.isDeleted, searchDto.getIsDeleted(), true));
    }


    public static <T, V> Specification<T> equal(SingularAttribute<T, V> field, V value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> (root, query, builder) -> {
            query.distinct(true);
            return builder.equal(root.get(field), value);
        });
    }

    public static <T, V> Specification<T> notEqual(SingularAttribute<T, V> field, V value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> (root, query, builder) -> {
            query.distinct(true);
            return builder.equal(root.get(field), value).not();
        });
    }


    private static <T, V> Specification<T> nullValueCheck(V value, boolean isSkipNullValues, Supplier<Specification<T>> specificationSupplier) {
        return value == null && isSkipNullValues ? EMPTY_SPECIFICATION : specificationSupplier.get();
    }

    private static <T, V> Specification<T> nullValueCheck(V value, V value2, boolean isSkipNullValues, Supplier<Specification<T>> specificationSupplier) {
        return value == null && value2 == null & isSkipNullValues ? EMPTY_SPECIFICATION : specificationSupplier.get();
    }


    public static <T, V> Specification<T> in(SingularAttribute<T, V> field, Collection<V> value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> {
            return (root, query, builder) -> {
                query.distinct(true);
                return root.get(field).in(value);
            };
        });
    }


    public static <T, V> Specification<T> notIn(SingularAttribute<T, V> field, Collection<V> value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> {
            return (root, query, builder) -> {
                query.distinct(true);
                return root.get(field).in(value).not();
            };
        });
    }


    public static <T> Specification<T> like(SingularAttribute<T, String> field, String value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> {
            return ((root, query, builder) -> {
                query.distinct(true);
                return builder.like(root.get(field), "%" + value + "%");
            });
        });
    }

    public static <T> Specification<T> likeLowerCase(SingularAttribute<T, String> field, String value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> {
            return ((root, query, builder) -> {
                query.distinct(true);
                return builder.like(builder.lower(root.get(field)), "%" + value.toLowerCase() + "%");
            });
        });
    }

    public static <T> Specification<T> between(SingularAttribute<T, ZonedDateTime> field,
                                               ZonedDateTime valueFrom,
                                               ZonedDateTime valueTo,
                                               boolean isSkipNullValues) {
        return nullValueCheck(valueFrom, valueTo, isSkipNullValues, () -> {
            return ((root, query, builder) -> {
                if (valueFrom == null) {
                    return builder.lessThanOrEqualTo(root.get(field), valueTo);
                } else if (valueTo == null) {
                    return builder.greaterThanOrEqualTo(root.get(field), valueFrom);
                } else {
                    return builder.between(root.get(field), valueFrom, valueTo);
                }
            });
        });
    }
}
