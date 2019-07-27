package com.github.jinahya.sakila.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static com.github.jinahya.sakila.persistence.EntityService.entityName;
import static com.github.jinahya.sakila.persistence.EntityService.singularAttribute;
import static java.util.concurrent.ThreadLocalRandom.current;

interface FullNamedEntityService<T extends FullNamedEntity> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedEntity> @PositiveOrZero long countByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        if (current().nextBoolean()) {
            final Query query = entityManager.createQuery(
                    "SELECT COUNT(e) " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName");
            query.setParameter("firstName", firstName);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager.createQuery(
                    "SELECT COUNT(e) " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName",
                    Long.class
            );
            typedQuery.setParameter("firstName", firstName);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<T> from = criteriaQuery.from(entityClass);
            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME), firstName));
            final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(from));
        //final SingularAttribute<FullNamedEntity, String> firstNameAttribute = FullNamedEntity_.firstName;
        final SingularAttribute<? super T, String> firstNameAttribute
                = singularAttribute(entityManager, entityClass, FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(firstNameAttribute), firstName));
        final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    static <T extends FullNamed> @NotNull List<@NotNull T> listByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamed> @PositiveOrZero long countByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamed> @NotNull List<@NotNull T> listByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByFirstName(@NotNull String firstName);

    @NotNull List<@NotNull T> listByFirstName(@NotNull String firstName);

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByLastName(@NotNull String lastName);

    @NotNull List<@NotNull T> listByLastName(@NotNull String lastName);
}
