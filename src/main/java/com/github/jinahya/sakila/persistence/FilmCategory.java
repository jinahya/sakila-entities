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
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(FilmCategoryId.class)
@Table(name = FilmCategory.TABLE_NAME)
public class FilmCategory implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "film_category";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_FILM_ID = "film_id";

    public static final String ATTRIBUTE_NAME_FILM = "film";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CATEGORY_ID = "category_id";

    public static final String ATTRIBUTE_NAME_CATEGORY = "category";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmCategory() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "film=" + film
               + ",category=" + category
               + ",lastUpdate=" + lastUpdate
               + "}";
    }

    // TODO: 2019-07-10 Add equals/hashCode if it's believed to be required

    // ------------------------------------------------------------------------------------------------------------ film

    public Film getFilm() {
        return film;
    }

    public void setFilm(final Film film) {
        this.film = film;
    }

    // -------------------------------------------------------------------------------------------------------- category
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    // ----------------------------------------------------------------------------------------------------- lastUpdated
    public Date getLastUpdate() {
        // TODO: 2019-07-10 return as a copy
        return lastUpdate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_FILM_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FILM)
    private Film film;

    @NotNull
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_CATEGORY_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CATEGORY)
    private Category category;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = BaseEntity.COLUMN_NAME_LAST_UPDATE, nullable = false, insertable = false, updatable = false)
    @NamedAttribute(BaseEntity.ATTRIBUTE_NAME_LAST_UPDATE)
    private Date lastUpdate;
}