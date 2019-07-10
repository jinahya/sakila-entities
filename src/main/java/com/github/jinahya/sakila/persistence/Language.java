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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.sakila.persistence.Language.COLUMN_NAME_LANGUAGE_ID;
import static com.github.jinahya.sakila.persistence.Language.TABLE_NAME;

@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_LANGUAGE_ID, nullable = false))
@Entity
@Table(name = TABLE_NAME)
public class Language extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "language";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LANGUAGE_ID = "language_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_NAME = "name";

    public static final String ATTRIBUTE_NAME_NAME = "name";

    public static final int SIZE_MIN_NAME = 0;

    public static final int SIZE_MAX_NAME = 20;

    // -----------------------------------------------------------------------------------------------------------------
    @Deprecated
    public static final String ATTRIBUTE_NAME_FILMS = "films";

    public static final int SIZE_MIN_FILMS = 0;

    public static final int SIZE_MAX_FILMS = Integer.MAX_VALUE;

    // -----------------------------------------------------------------------------------------------------------------
    @Deprecated
    public static final String ATTRIBUTE_NAME_FILMS_RECORDED = "filmsRecorded";

    public static final int SIZE_MIN_FILMS_RECORDED = 0;

    public static final int SIZE_MAX_FILMS_RECORDED = Integer.MAX_VALUE;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Language() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "name=" + name
               + "}";
    }

    // ------------------------------------------------------------------------------------------------------------ name
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // ----------------------------------------------------------------------------------------------------------- films
    @Deprecated
    public Set<Film> getFilms() {
        if (films == null) {
            films = new HashSet<>();
        }
        return films;
    }

    @Deprecated
    public boolean addFilm(final Film film) {
        if (film == null) {
            throw new NullPointerException("film is null");
        }
        final boolean added = getFilms().add(film);
        if (film.getLanguage() != this) {
            film.setLanguage(this);
        }
        return added;
    }

    // --------------------------------------------------------------------------------------------------- filmsRecorded
    @Deprecated
    public Set<Film> getFilmsRecorded() {
        if (filmsRecorded == null) {
            filmsRecorded = new HashSet<>();
        }
        return filmsRecorded;
    }

    @Deprecated
    public boolean addFilmRecorded(final Film filmRecorded) {
        if (filmRecorded == null) {
            throw new NullPointerException("filmRecorded is null");
        }
        final boolean added = getFilmsRecorded().add(filmRecorded);
        if (filmRecorded.getOriginalLanguage() != this) {
            filmRecorded.setOriginalLanguage(this);
        }
        return added;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_NAME, max = SIZE_MAX_NAME)
    @NotBlank
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_NAME)
    private String name;

    // -----------------------------------------------------------------------------------------------------------------
    @Deprecated
    @OneToMany(mappedBy = Film.ATTRIBUTE_NAME_LANGUAGE)
    @NamedAttribute(ATTRIBUTE_NAME_FILMS)
    private @Size(min = SIZE_MIN_FILMS, max = SIZE_MAX_FILMS) Set<@NotNull Film> films;

    @Deprecated
    @OneToMany(mappedBy = Film.ATTRIBUTE_NAME_ORIGINAL_LANGUAGE)
    @NamedAttribute(ATTRIBUTE_NAME_FILMS_RECORDED)
    private @Size(min = SIZE_MIN_FILMS_RECORDED, max = SIZE_MAX_FILMS_RECORDED) Set<@NotNull Film> filmsRecorded;
}
