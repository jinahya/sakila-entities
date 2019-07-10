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

import java.io.Serializable;
import java.util.Objects;

/**
 * An id class for {@link FilmActor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class FilmActorId implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmActorId() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "actor=" + actor
               + ",film=" + film
               + "}";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilmActorId)) {
            return false;
        }
        final FilmActorId that = (FilmActorId) obj;
        return getActor() == that.getActor() && getFilm() == that.getFilm();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActor(), getFilm());
    }

    // ----------------------------------------------------------------------------------------------------------- actor
    public int getActor() {
        return actor;
    }

    public void setActor(final int actor) {
        this.actor = actor;
    }

    // ------------------------------------------------------------------------------------------------------------ film
    public int getFilm() {
        return film;
    }

    public void setFilm(final int film) {
        this.film = film;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NamedAttribute(FilmActor.ATTRIBUTE_NAME_ACTOR)
    private int actor;

    @NamedAttribute(FilmActor.ATTRIBUTE_NAME_FILM)
    private int film;
}
