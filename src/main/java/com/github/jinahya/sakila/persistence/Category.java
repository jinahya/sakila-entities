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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.sakila.persistence.Category.COLUMN_NAME_CATEGORY_ID;
import static com.github.jinahya.sakila.persistence.Category.TABLE_NAME;

/**
 * An entity for {@value #TABLE_NAME} table.
 * <blockquote>
 * The {@code category} table lists the categories that can be assigned to a film.
 * <p>
 * The category table is joined to the {@link Film film} table by means of the {@link FilmCategory film_category}
 * table.
 * </blockquote>
 */
@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_CATEGORY_ID, nullable = false))
@Entity
@Table(name = TABLE_NAME)
public class Category extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of this entity class. The value is {@value}.
     */
    public static final String TABLE_NAME = "category";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The column name for {@link #ATTRIBUTE_NAME_ID} attribute. The value is {@value}.
     */
    public static final String COLUMN_NAME_CATEGORY_ID = "category_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_NAME = "name";

    public static final String ATTRIBUTE_NAME_NAME = "name";

    public static final int SIZE_MIN_NAME = 0; // empty?

    public static final int SIZE_MAX_NAME = 25;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-11 Remove!!!
    @Deprecated
    public static final String ATTRIBUTE_NAME_FILMS = "films";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Category() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "name=" + name
               + "}";
    }

    // TODO: 2019-07-11 equals/hashCode???

    // ------------------------------------------------------------------------------------------------------------ name

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_NAME} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_NAME} attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces the current value of {@value #ATTRIBUTE_NAME_NAME} attribute with specified value.
     *
     * @param name new value for {@value #ATTRIBUTE_NAME_NAME} attribute.
     */
    public void setName(final String name) {
        this.name = name;
    }

    // ----------------------------------------------------------------------------------------------------------- films

    /**
     * Returns all films categorized to this category.
     *
     * @return a set of films categories to this category.
     * @deprecated Going to be removed!!!
     */
    // TODO: 2019-07-11 Remove!!!
    @Deprecated // forRemoval = true
    public Set<Film> getFilms() {
        if (films == null) {
            films = new HashSet<>();
        }
        return films;
    }

    /**
     * Categorize specified film to this category.
     *
     * @param film the film to be categorized this this category.
     * @return {@code true} if specified film is not already categorized to this category; {@code false} otherwise.
     */
    // TODO: 2019-07-11 Remove!!!
    @Deprecated // forRemoval = true
    public boolean addFilm(final Film film) {
        if (film == null) {
            throw new NullPointerException("film is null");
        }
        final boolean filmAdded = getFilms().add(film);
        if (!film.getCategories().contains(this)) {
            final boolean addedToFilm = film.addCategory(this);
        }
        return filmAdded;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_NAME, max = SIZE_MAX_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_NAME)
    private String name;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-11 Remove!!!
    @Deprecated // forRemoval = true
    @ManyToMany(mappedBy = Film.ATTRIBUTE_NAME_CATEGORIES)
    @NamedAttribute(ATTRIBUTE_NAME_FILMS)
    private Set<Film> films;
}
