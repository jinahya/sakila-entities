package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
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

import static com.github.jinahya.sakila.persistence.EntityService.entityName;
import static com.github.jinahya.sakila.persistence.EntityService.singularAttribute;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

interface FullNamedBaseEntityService<T extends FullNamedBaseEntity> extends FullNamedService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedBaseEntity> @PositiveOrZero long countByFirstName(
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
        //final SingularAttribute<FullNamedBaseEntity, String> firstNameAttribute = FullNamedBaseEntity_.firstName;
        final SingularAttribute<? super T, String> firstNameAttribute
                = singularAttribute(entityManager, entityClass, FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(firstNameAttribute), firstName));
        final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    static <T extends FullNamedBaseEntity> @NotNull List<@NotNull T> listByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName,
            @PositiveOrZero @Nullable Integer firstResult, @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager.createQuery(
                    "SELECT e " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName");
            query.setParameter("firstName", firstName);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<T> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<T> typedQuery = entityManager.createQuery(
                    "SELECT e " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName",
                    entityClass
            );
            typedQuery.setParameter("firstName", firstName);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            final Root<T> from = criteriaQuery.from(entityClass);
            criteriaQuery.select(from);
            criteriaQuery.where(criteriaBuilder.equal(from.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME), firstName));
            final TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(from);
        //final SingularAttribute<FullNamedBaseEntity, String> firstNameAttribute = FullNamedBaseEntity_.firstName;
        final SingularAttribute<? super T, String> firstNameAttribute
                = singularAttribute(entityManager, entityClass, FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(firstNameAttribute), firstName));
        final TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedBaseEntity> @PositiveOrZero long countByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamedBaseEntity> @NotNull List<@NotNull T> listByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName,
            @PositiveOrZero @Nullable Integer firstResult, @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    default @PositiveOrZero long countByFirstName(@NotNull final String firstName) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return countByFirstName(entityManager, entityClass, firstName);
    }

    @Override
    default @NotNull List<@NotNull T> listByFirstName(@NotNull final String firstName,
                                                      @PositiveOrZero @Nullable Integer firstResult,
                                                      @Positive @Nullable final Integer maxResults) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return listByFirstName(entityManager, entityClass, firstName, firstResult, maxResults);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    default @PositiveOrZero long countByLastName(@NotNull final String lastName) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return countByLastName(entityManager, entityClass, lastName);
    }

    @Override
    default @NotNull List<@NotNull T> listByLastName(@NotNull final String lastName,
                                                     @PositiveOrZero @Nullable Integer lastResult,
                                                     @Positive @Nullable final Integer maxResults) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return listByLastName(entityManager, entityClass, lastName, lastResult, maxResults);
    }
}
