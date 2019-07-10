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

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

/**
 * An embeddable id class for {@link FilmCategory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class FilmCategoryId implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmCategoryId() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "film=" + film
               + ",category=" + category
               + "}";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilmCategoryId)) {
            return false;
        }
        final FilmCategoryId that = (FilmCategoryId) obj;
        return getFilm() == that.getFilm() && getCategory() == that.getCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilm(), getCategory());
    }

    // ------------------------------------------------------------------------------------------------------------ film
    public int getFilm() {
        return film;
    }

    public void setFilm(final int film) {
        this.film = film;
    }

    // -------------------------------------------------------------------------------------------------------- category
    public int getCategory() {
        return category;
    }

    public void setCategory(final int category) {
        this.category = category;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = false)
    @Column(name = FilmCategory.COLUMN_NAME_FILM_ID, nullable = false)
    @NamedAttribute(FilmCategory.ATTRIBUTE_NAME_FILM)
    private int film;

    @Basic(optional = false)
    @Column(name = FilmCategory.COLUMN_NAME_CATEGORY_ID, nullable = false)
    @NamedAttribute(FilmCategory.ATTRIBUTE_NAME_CATEGORY)
    private int category;
}
