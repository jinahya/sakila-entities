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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Staff.COLUMN_NAME_STAFF_ID;
import static com.github.jinahya.mysql.sakila.persistence.Staff.TABLE_NAME;

@AttributeOverrides({
                            @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_STAFF_ID))
                    })
@Entity
@Table(name = TABLE_NAME)
public class Staff extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "staff";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STAFF_ID = "staff_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    public static final String ATTRIBUTE_NAME_FIRST_NAME = "firstName";

    public static final int SIZE_MAX_FIRST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static final String ATTRIBUTE_NAME_LAST_NAME = "lastName";

    public static final int SIZE_MAX_LAST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    public static final String ATTRIBUTE_NAME_ADDRESS = "address";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_PICTURE = "picture";

    public static final String ATTRIBUTE_NAME_PICTURE = "picture";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMAIL = "email";

    public static final String ATTRIBUTE_NAME_EMAIL = "email";

    public static final int SIZE_MAX_EMAIL = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STORE_ID = "store_id";

    public static final String ATTRIBUTE_NAME_STORE = "store";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ACTIVE = "active";

    public static final String ATTRIBUTE_NAME_ACTIVE = "active";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_USERNAME = "username";

    public static final String ATTRIBUTE_NAME_USERNAME = "username";

    public static final int SIZE_MAX_USERNAME = 16;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_PASSWORD = "password";

    public static final String ATTRIBUTE_NAME_PASSWORD = "password";

    public static final int SIZE_MAX_PASSWORD = 40;

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = SIZE_MAX_FIRST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FIRST_NAME)
    private String firstName;

    @Size(max = SIZE_MAX_LAST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_NAME)
    private String lastName;

    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS)
    private Address address;

    @Lob
    @Column(name = COLUMN_NAME_PICTURE)
    @NamedAttribute(ATTRIBUTE_NAME_PICTURE)
    private byte[] picture;

    @Size(max = SIZE_MAX_EMAIL)
    @Basic
    @Column(name = COLUMN_NAME_EMAIL)
    @NamedAttribute(ATTRIBUTE_NAME_EMAIL)
    private String email;

    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_STORE)
    private Store store;

    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ACTIVE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ACTIVE)
    private boolean active;

    @Size(max = SIZE_MAX_USERNAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_USERNAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_USERNAME)
    private String username;

    @Size(max = SIZE_MAX_PASSWORD)
    @Basic
    @Column(name = COLUMN_NAME_PASSWORD)
    @NamedAttribute(ATTRIBUTE_NAME_PASSWORD)
    private String password;
}
