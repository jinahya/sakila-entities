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

import static com.github.jinahya.mysql.sakila.persistence.Address.COLUMN_NAME_ADDRESS_ID;
import static com.github.jinahya.mysql.sakila.persistence.Address.TABLE_NAME;
import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;

@AttributeOverrides({
                            @AttributeOverride(name = ATTRIBUTE_NAME_ID,
                                               column = @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false))
                    })
@Entity
@Table(name = TABLE_NAME)
public class Address extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "address";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS = "address";

    public static final String ATTRIBUTE_NAME_ADDRESS = "address";

    public static final int SIZE_MAX_ADDRESS = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS2 = "address2";

    public static final String ATTRIBUTE_NAME_ADDRESS2 = "address2";

    public static final int SIZE_MAX_ADDRESS2 = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_DISTRICT = "district";

    public static final String ATTRIBUTE_NAME_DISTRICT = "district";

    public static final int SIZE_MAX_DISTRICT = 20;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CITY_ID = "city_id";

    public static final String ATTRIBUTE_NAME_CITY = "city";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_POSTAL_CODE = "postal_code";

    public static final String ATTRIBUTE_NAME_POSTAL_CODE = "postalCode";

    public static final int SIZE_MAX_POSTAL_CODE = 10;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_PHONE = "phone";

    public static final String ATTRIBUTE_NAME_PHONE = "phone";

    public static final int SIZE_MAX_PHONE = 10;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LOCATION = "location";

    public static final String ATTRIBUTE_NAME_LOCATION = "location";

    public static final int SIZE_MAX_LOCATION_FOR_POINT = 21;

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = SIZE_MAX_ADDRESS)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS)
    private String address;

    @Size(max = SIZE_MAX_ADDRESS2)
    @Basic
    @Column(name = COLUMN_NAME_ADDRESS2)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS2)
    private String address2;

    @Size(max = SIZE_MAX_DISTRICT)
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_DISTRICT)
    @NamedAttribute(ATTRIBUTE_NAME_DISTRICT)
    private String district;

    @ManyToOne(optional = false)
    @NotNull
    @Column(name = COLUMN_NAME_CITY_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CITY)
    private City city;

    @Size(max = SIZE_MAX_POSTAL_CODE)
    @Basic
    @Column(name = COLUMN_NAME_POSTAL_CODE)
    @NamedAttribute(ATTRIBUTE_NAME_POSTAL_CODE)
    private String postalCode;

    @Size(max = SIZE_MAX_PHONE)
    @NotNull
    @Column(name = COLUMN_NAME_PHONE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_PHONE)
    private String phone;

    @NotNull
    @Lob
    @Column(name = COLUMN_NAME_LOCATION, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LOCATION)
    private byte[] location;
}
