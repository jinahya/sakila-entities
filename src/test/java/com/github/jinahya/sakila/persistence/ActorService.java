package com.github.jinahya.sakila.persistence;

import java.util.stream.Stream;

/**
 * A service class for {@link Actor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
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
     * Returns a stream of actors, whose {@link FullName#ATTRIBUTE_NAME_LAST_NAME} attribute <i>optionally</i> matches
     * to specified value, ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} in either ascending or descending
     * indicated by specified flag.
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
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a stream of actors, whose {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute <i>optionally</i> matches
     * to specified value, ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME} in either ascending or descending
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
        throw new UnsupportedOperationException("not implemented yet");
    }
}
