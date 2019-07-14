package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.TypedQuery;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Actor.ENTITY_NAME;
import static com.github.jinahya.sakila.persistence.FullName.ATTRIBUTE_NAME_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.ATTRIBUTE_NAME_LAST_NAME;
import static com.github.jinahya.sakila.persistence.FullNameEmbedded.ATTRIBUTE_NAME_FULL_NAME;
import static java.util.Optional.ofNullable;

/**
 * A service class for {@link Actor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class ActorService extends BaseEntityService<Actor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorService() {
        super(Actor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a stream of actors whose {@link FullName#ATTRIBUTE_NAME_LAST_NAME} attribute <i>optionally</i> matches to
     * specified value ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} in either ascending or descending indicated
     * by specified flag.
     *
     * @param lastName       a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to <i>optionally</i> match; may be
     *                       {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally matched, actors.
     */
    public Stream<Actor> streamOrderedByFirstName(final String lastName, final boolean ascendingOrder,
                                                  final Integer firstResult, final Integer maxResults) {
        final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM " + ENTITY_NAME + " AS a");
        if (lastName != null) {
            queryBuilder.append(
                    " WHERE a." + ATTRIBUTE_NAME_FULL_NAME + "." + ATTRIBUTE_NAME_LAST_NAME + " = :lastName");
        }
        queryBuilder
                .append(" ORDER BY a." + ATTRIBUTE_NAME_FULL_NAME + "." + ATTRIBUTE_NAME_FIRST_NAME + " ")
                .append(ascendingOrder ? "ASC" : "DESC");
        final String queryString = queryBuilder.toString();
        log.debug("queryString: {}", queryString);
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryString, Actor.class);
        ofNullable(lastName).ifPresent(v -> typedQuery.setParameter("lastName", v));
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }

    /**
     * Returns a stream of actors whose {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute <i>optionally</i> matches
     * to specified value ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME} in either ascending or descending
     * indicated by specified flag.
     *
     * @param firstName      a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to <i>optionally</i> match; may be
     *                       {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally matched, actors.
     */
    public Stream<Actor> streamOrderedByLastName(final String firstName, final boolean ascendingOrder,
                                                 final Integer firstResult, final Integer maxResults) {
        final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM " + ENTITY_NAME + " AS a");
        if (firstName != null) {
            queryBuilder.append(
                    " WHERE a." + ATTRIBUTE_NAME_FULL_NAME + "." + ATTRIBUTE_NAME_FIRST_NAME + " = :firstName");
        }
        queryBuilder
                .append(" ORDER BY a." + ATTRIBUTE_NAME_FULL_NAME + "." + ATTRIBUTE_NAME_FIRST_NAME + " ")
                .append(ascendingOrder ? "ASC" : "DESC");
        final String queryString = queryBuilder.toString();
        log.debug("queryString: {}", queryString);
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryString, Actor.class);
        ofNullable(firstName).ifPresent(v -> typedQuery.setParameter("firstName", v));
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }
}
