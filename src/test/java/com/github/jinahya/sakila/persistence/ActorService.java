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

import java.util.List;

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
     * Returns a list of actors, which each {@link FullName#ATTRIBUTE_NAME_LAST_NAME} attribute <i>optionally</i>
     * matches to specified value, ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} in either ascending or
     * descending indicated by specified flag.
     *
     * @param lastName       a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to <i>optionally</i> match; may be
     *                       {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a list of, optionally matched, actors.
     */
    public List<Actor> listSortedByFirstName(final String lastName, final boolean ascendingOrder,
                                             final Integer firstResult, final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of actors, which each {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute <i>optionally</i>
     * matches to specified value, ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME} in either ascending or
     * descending indicated by specified flag.
     *
     * @param firstName      a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to <i>optionally</i> match; may be
     *                       {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a list of, optionally matched, actors.
     */
    public List<Actor> listSortedByLastName(final String firstName, final boolean ascendingOrder,
                                            final Integer firstResult, final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
