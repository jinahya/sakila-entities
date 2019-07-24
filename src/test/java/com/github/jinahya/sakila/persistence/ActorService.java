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

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A service class for {@link Actor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class ActorService extends BaseEntityService<Actor> implements FullNamedBaseEntityService<Actor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorService() {
        super(Actor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Lists actors whose {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute and {@link
     * Actor#ATTRIBUTE_NAME_LAST_NAME lastName} attribute match to specified values ordered by {@link
     * BaseEntity#ATTRIBUTE_NAME_ID} attribute in ascending order.
     *
     * @param firstName   a value for {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute to match; {@code null}
     *                    for skipping matching.
     * @param lastName    a value for {@link Actor#ATTRIBUTE_NAME_LAST_NAME lastName} attribute to match; {@code null}
     *                    for skipping matching.
     * @param firstResult position of the first result, numbered from 0.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of actors.
     */
    public List<Actor> listSortedByIdInAscendingOrder(@Nullable final String firstName, @Nullable final String lastName,
                                                      @Nullable final Integer firstResult,
                                                      @Nullable final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of actors, whose {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} attributes match to specified
     * value, ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute in either ascending order or
     * descending order indicated by specified flag.
     *
     * @param lastName       a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} to match; {@code null} for
     *                       skipping the criterion.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    position of the first result, numbered from 0.
     * @param maxResults     maximum number of results to retrieve.
     * @return a list of actors.
     */
    public List<Actor> listSortedByFirstName(@Nullable final String lastName, final boolean ascendingOrder,
                                             @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of actors, whose {@link FullName#ATTRIBUTE_NAME_FIRST_NAME firstName} attributes match to
     * specified value, ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} in either ascending order or
     * descending order indicated by specified flag.
     *
     * @param firstName      a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} to match; {@code null} for
     *                       skipping the criterion.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    position of the first result, numbered from 0.
     * @param maxResults     maximum number of results to retrieve.
     * @return a list of actors.
     */
    public List<Actor> listSortedByLastName(@Nullable final String firstName, final boolean ascendingOrder,
                                            @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
