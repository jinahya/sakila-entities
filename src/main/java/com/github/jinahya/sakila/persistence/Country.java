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
import static com.github.jinahya.sakila.persistence.Country.COLUMN_NAME_COUNTRY_ID;
import static com.github.jinahya.sakila.persistence.Country.TABLE_NAME;
import static java.util.Objects.requireNonNull;

@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false))
@Entity
@Table(name = TABLE_NAME)
public class Country extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "country";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_COUNTRY = "country";

    public static final String ATTRIBUTE_NAME_COUNTRY = "country";

    public static final int SIZE_MIN_COUNTRY = 0; // empty?

    public static final int SIZE_MAX_COUNTRY = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String ATTRIBUTE_NAME_CITIES = "cities";

    public static final int SIZE_MIN_CITIES = 0;

    public static final int SIZE_MAX_CITIES = Integer.MAX_VALUE;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Country() {
        super();
    }

    // --------------------------------------------------------------------------------------------------------- country

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    // ---------------------------------------------------------------------------------------------------------- cities
    @Deprecated
    public Set<City> getCities() {
        if (cities == null) {
            cities = new HashSet<>();
        }
        return cities;
    }

    @Deprecated
    public boolean addCity(final City city) {
        final boolean cityAdded = getCities().add(requireNonNull(city, "city is null"));
        if (city.getCountry() != this) {
            city.setCountry(this);
        }
        return cityAdded;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_COUNTRY, max = SIZE_MAX_COUNTRY)
    @NotBlank
    @Column(name = COLUMN_NAME_COUNTRY, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_COUNTRY)
    private String country;

    // -----------------------------------------------------------------------------------------------------------------
    @Deprecated
    @Size(min = SIZE_MIN_CITIES, max = SIZE_MAX_CITIES)
    @OneToMany(cascade = {/* ?? */}, mappedBy = City.ATTRIBUTE_NAME_COUNTRY)
    @NamedAttribute(ATTRIBUTE_NAME_CITIES)
    private Set<@NotNull City> cities;
}
