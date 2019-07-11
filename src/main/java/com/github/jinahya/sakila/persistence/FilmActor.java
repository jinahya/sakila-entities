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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_LAST_UPDATE;
import static com.github.jinahya.sakila.persistence.BaseEntity.COLUMN_NAME_LAST_UPDATE;

/**
 * An entity class for {@value #TABLE_NAME} table.
 */
@IdClass(FilmActorId.class)
@Entity
@Table(name = FilmActor.TABLE_NAME)
public class FilmActor implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of this entity class. The value is {@value}.
     */
    public static final String TABLE_NAME = "film_actor";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The table column name for {@value #ATTRIBUTE_NAME_ACTOR} attribute. The value is {@value}.
     * <blockquote>
     * A foreign key identifying the actor.
     * </blockquote>
     * {@code SMALLINT(5) PK NN UN}
     */
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    public static final String ATTRIBUTE_NAME_ACTOR = "actor";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The database column name for {@link #ATTRIBUTE_NAME_FILM} attribute. The value is {@value}.
     * <blockquote>
     * A foreign key identifying the film.
     * </blockquote>
     * {@code SMALLINT(5) PK NN UN}
     */
    public static final String COLUMN_NAME_FILM_ID = "film_id";

    public static final String ATTRIBUTE_NAME_FILM = "film";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts films mapped to specified actor.
     *
     * @param entityManager an entity manager.
     * @param actor         the actor whose films are counted.
     * @return the number of films mapped to specified actor.
     */
    public static @PositiveOrZero long countFilms(@NotNull final EntityManager entityManager,
                                                  @NotEmpty final Actor actor) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists films mapped to specified actor ordered by {@link Film#ATTRIBUTE_NAME_TITLE} attribute in ascending order.
     *
     * @param entityManager an entity manager.
     * @param actor         the actor to match.
     * @param firstResult   a value for {@link TypedQuery#setFirstResult(int)}
     * @param maxResults    a value for {@link TypedQuery#setMaxResults(int)}
     * @return a list of films.
     */
    public static @NotNull List<Film> listFilms(@NotNull final EntityManager entityManager,
                                                @NotEmpty final Actor actor,
                                                @PositiveOrZero final Integer firstResult,
                                                @Positive final Integer maxResults) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Counts distinct films mapped to any of specified actors.
     *
     * @param entityManager an entity manager.
     * @param actors        the actors whose films are counted.
     * @return the number of distinct films mapped to any of specified actors.
     */
    public static @PositiveOrZero long countFilms(@NotNull final EntityManager entityManager,
                                                  @NotEmpty final Collection<@NotNull ? extends Actor> actors) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Returns a stream of distinct films mapped to any of specified actors ordered by {@link Film#ATTRIBUTE_NAME_TITLE}
     * in ascending order.
     *
     * @param entityManager an entity manager.
     * @param actors        the actors to match.
     * @param firstResult   a value for {@link TypedQuery#setFirstResult(int)}
     * @param maxResults    a value for {@link TypedQuery#setMaxResults(int)}
     * @return a stream of distinct films mapped to any of specified actors.
     */
    public static @NotNull Stream<Film> streamFilms(@NotNull final EntityManager entityManager,
                                                    @NotEmpty final Collection<@NotNull ? extends Actor> actors,
                                                    @PositiveOrZero final Integer firstResult,
                                                    @Positive final Integer maxResults) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count actors mapped to specified film.
     *
     * @param entityManager an entity manager.
     * @param film          the film to match.
     * @return the number of actors mapped to specified film.
     */
    public static @PositiveOrZero long countActors(@NotNull final EntityManager entityManager,
                                                   @NotEmpty final Film film) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists actors mapped to specified film ordered by {@link Actor#ATTRIBUTE_NAME_FIRST_NAME} in ascending order.
     *
     * @param entityManager an entity manager.
     * @param film          the film to match.
     * @param firstResult   a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults    a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    public static @NotNull List<Actor> listActors(@NotNull final EntityManager entityManager,
                                                  @NotEmpty final Film film,
                                                  @PositiveOrZero final Integer firstResult,
                                                  @Positive final Integer maxResults) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Count distinct actors mapped to any of specified films.
     *
     * @param entityManager an entity manager.
     * @param films         the films to match.
     * @return the number of actors mapped to specified film.
     */
    public static @PositiveOrZero long countActors(@NotNull final EntityManager entityManager,
                                                   @NotEmpty final Collection<@NotNull ? extends Film> films) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Returns a stream of distinct actors mapped to any of specified films ordered by {@link
     * Actor#ATTRIBUTE_NAME_LAST_NAME} attribute in ascending order.
     *
     * @param entityManager an entity manager.
     * @param films         the films to match.
     * @param firstResult   a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults    a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a stream of distinct actors mapped to any of specified films.
     */
    public static @NotNull Stream<Actor> streamActors(@NotNull final EntityManager entityManager,
                                                      @NotEmpty final Collection<@NotNull ? extends Film> films,
                                                      @PositiveOrZero final Integer firstResult,
                                                      @Positive final Integer maxResults) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmActor() {
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
        return super.toString() + "{" +
               "actor=" + actor
               + ",film=" + film
               + ",lastUpdate=" + lastUpdate
               + '}';
    }

    // TODO: 2019-07-10 Add equals/hashCode ???

    // ----------------------------------------------------------------------------------------------------------- actor
    public Actor getActor() {
        return actor;
    }

    public void setActor(final Actor actor) {
        this.actor = actor;
    }

    // ------------------------------------------------------------------------------------------------------------ film
    public Film getFilm() {
        return film;
    }

    public void setFilm(final Film film) {
        this.film = film;
    }

    // ------------------------------------------------------------------------------------------------------ lastUpdate

    /**
     * Returns the current value of {@code lastUpdate} attribute.
     *
     * @return the current value of {@code lastUpdte} attribute.
     */
    public Date getLastUpdate() {
        // TODO: 2019-07-11 Make to return a copy!!!
        return lastUpdate;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @NotNull
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_ACTOR_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ACTOR)
    private Actor actor;

    @NotNull
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FILM)
    private Film film;

    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_LAST_UPDATE, nullable = false, insertable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_UPDATE)
    private Date lastUpdate;
}
