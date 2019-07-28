package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

abstract class AbstractFullNamedBaseEntityService<T extends FullNamedBaseEntity>
        extends BaseEntityService<T>
        implements FullNamedBaseEntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    AbstractFullNamedBaseEntityService(final Class<T> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public @PositiveOrZero long countByFirstName(@NotNull final String firstName) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT COUNT(e) " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName");
            query.setParameter("firstName", firstName);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(e) " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName",
                    Long.class
            );
            typedQuery.setParameter("firstName", firstName);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<T> from = criteriaQuery.from(entityClass);
            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME), firstName));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(from));
        //final SingularAttribute<FullNamedBaseEntity, String> firstNameAttribute = FullNamedBaseEntity_.firstName;
        final SingularAttribute<? super T, String> firstNameAttribute
                = singularAttribute(entityManager(), entityClass, FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(firstNameAttribute), firstName));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public @NotNull List<@NotNull T> listByFirstName(@NotNull final String firstName,
                                                     @PositiveOrZero @Nullable final Integer firstResult,
                                                     @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public @PositiveOrZero long countByLastName(@NotNull final String lastName) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public @NotNull List<@NotNull T> listByLastName(@NotNull final String lastName,
                                                    @PositiveOrZero @Nullable final Integer lastResult,
                                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
