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

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.github.jinahya.sakila.persistence.Actor.TABLE_NAME;
import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.sakila.persistence.FullNamedEntity.COLUMN_NAME_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullNamedEntity.COLUMN_NAME_LAST_NAME;

/**
 * An entity class for binding {@value TABLE_NAME} table.
 * <blockquote>
 * The {@code actor} table lists information for all actors.
 * <p>
 * The {@code actor} table is joined to the {@link Film film} table by means of the {@link FilmActor film_actor} table.
 * </blockquote>
 *
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">The actor table (Sakila Sample
 * Database)</a>
 */
@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = Actor.COLUMN_NAME_ACTOR_ID, nullable = false))
@Entity(name = Actor.ENTITY_NAME)
@Table(name = TABLE_NAME)
public class Actor extends BaseEntity implements FullNamed {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of {@link Actor} entity. The value is {@value}.
     */
    public static final String TABLE_NAME = "actor";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The value for {@link Entity#name()}.
     *
     * @deprecated Use default value.
     */
    // TODO: 2019-07-14 remove!!!
    @Deprecated // for Removal = true
    public static final String ENTITY_NAME = "Actor";

    static {
        assert ENTITY_NAME.equals(Actor.class.getSimpleName());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * {@code SMALLINT(5) PK NN UN AI}
     */
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    public static final String ATTRIBUTE_NAME_FILMS = "films";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Actor() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + "{"
               + "firstName=" + firstName
               + ",lastName=" + lastName
               + "}";
    }

    // ------------------------------------------------------------------------------------------------------- firstName
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    // -------------------------------------------------------------------------------------------------------- lastName
    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    // ----------------------------------------------------------------------------------------------------------- films
    // TODO: 2019-07-14 remove!!!
    @Deprecated // forRemoval = true
    public Set<Film> getFilms() {
        if (films == null) {
            films = new HashSet<>();
        }
        return films;
    }

    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    public boolean addFilm(final Film film) {
        if (film == null) {
            throw new NullPointerException("film is null");
        }
        final boolean filmAdded = getFilms().add(film);
        if (!film.getActors().contains(this)) {
            final boolean addedToFilm = film.addActor(this);
        }
        return filmAdded;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_FIRST_NAME, max = SIZE_MAX_FIRST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false, length = SIZE_MAX_FIRST_NAME)
    @NamedAttribute(ATTRIBUTE_NAME_FIRST_NAME)
    private String firstName;

    @Size(min = SIZE_MIN_LAST_NAME, max = SIZE_MAX_LAST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, length = SIZE_MAX_LAST_NAME)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_NAME)
    private String lastName;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-14 remove!!!
    @Deprecated
    @ManyToMany(cascade = {
            //CascadeType.ALL,
            //CascadeType.DETACH,
            //CascadeType.MERGE,
            //CascadeType.PERSIST,
            //CascadeType.REFRESH,
            //CascadeType.REMOVE
    })
    @JoinTable(name = FilmActor.TABLE_NAME,
               joinColumns = {
                       @JoinColumn(name = FilmActor.COLUMN_NAME_ACTOR_ID, referencedColumnName = COLUMN_NAME_ACTOR_ID)
               },
               inverseJoinColumns = {
                       @JoinColumn(name = FilmActor.COLUMN_NAME_FILM_ID,
                                   referencedColumnName = Film.COLUMN_NAME_FILM_ID)
               }
    )
    @NamedAttribute(ATTRIBUTE_NAME_FILMS)
    private Set<Film> films;
}
