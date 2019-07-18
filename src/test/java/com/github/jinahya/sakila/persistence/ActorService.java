package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

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
     * Finds the actor whose {@link Actor#ATTRIBUTE_NAME_FIRST_NAME} attribute and {@link
     * Actor#ATTRIBUTE_NAME_LAST_NAME} attribute match to specified values.
     *
     * @param firstName a value for {@link Actor#ATTRIBUTE_NAME_FIRST_NAME} attribute to match.
     * @param lastName  a value for {@link Actor#ATTRIBUTE_NAME_LAST_NAME} attribute to match.
     * @return an optional of found entity; empty if not found.
     */
    public Optional<Actor> findByName(final String firstName, final String lastName) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of actors, whose {@link FullName#ATTRIBUTE_NAME_LAST_NAME} attributes match to specified value,
     * ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} in either ascending or descending indicated by specified
     * flag.
     *
     * @param lastName       a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to match; may be {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a list of actors.
     */
    public List<Actor> listSortedByFirstName(@Nullable final String lastName, final boolean ascendingOrder,
                                             @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of actors, whose {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attributes match to specified value,
     * ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME} in either ascending or descending indicated by specified
     * flag.
     *
     * @param firstName      a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to match; may be {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a list of actors.
     */
    public List<Actor> listSortedByLastName(@Nullable final String firstName, final boolean ascendingOrder,
                                            @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
