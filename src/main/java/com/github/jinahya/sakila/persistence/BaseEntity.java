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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The attribute name of {@code ..._id} columns. The value is {@value}.
     */
    public static final String ATTRIBUTE_NAME_ID = "id";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The column name of {@value}.
     */
    public static final String COLUMN_NAME_LAST_UPDATE = "last_update";

    /**
     * The attribute name for {@link #COLUMN_NAME_LAST_UPDATE} column.
     */
    public static final String ATTRIBUTE_NAME_LAST_UPDATE = "lastUpdate";

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + "{"
               + "id=" + id
               + ",lastUpdate=" + lastUpdate
               + "}";
    }

    // -------------------------------------------------------------------------------------------------------------- id

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_ID} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_ID} attribute.
     */
    public Integer getId() {
        return id;
    }

    void setId(final Integer id) {
        this.id = id;
    }

    // ------------------------------------------------------------------------------------------------------ lastUpdate

    /**
     * Returns the current value of {@link #ATTRIBUTE_NAME_LAST_UPDATE} attribute.
     *
     * @return the current value of {@link #ATTRIBUTE_NAME_LAST_UPDATE} attribute.
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ..._id ...INT PK NN UN AI
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NamedAttribute(ATTRIBUTE_NAME_ID)
    private Integer id;

    // last_update TIMESTAMP NN CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    // !Payment !Customer
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_LAST_UPDATE, nullable = false, insertable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_UPDATE)
    private Date lastUpdate;
}
