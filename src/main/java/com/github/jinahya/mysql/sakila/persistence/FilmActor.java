package com.github.jinahya.mysql.sakila.persistence;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_LAST_UPDATE;
import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.COLUMN_NAME_LAST_UPDATE;
import static com.github.jinahya.mysql.sakila.persistence.FilmActor.TABLE_NAME;

@IdClass(FilmActorId.class)
@Entity
@Table(name = TABLE_NAME)
public class FilmActor implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "film_actor";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    public static final String ATTRIBUTE_NAME_ACTOR_ID = "actorId";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_FILM_ID = "film_id";

    public static final String ATTRIBUTE_NAME_FILM_ID = "filmId";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmActor() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------
    public int getActorId() {
        return actorId;
    }

    public void setActorId(final int actorId) {
        this.actorId = actorId;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(final int filmId) {
        this.filmId = filmId;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Id
    @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ACTOR_ID)
    private int actorId;

    @Id
    @Column(name = COLUMN_NAME_FILM_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FILM_ID)
    private int filmId;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_LAST_UPDATE, nullable = false, insertable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_UPDATE)
    private Date lastUpdate;
}
