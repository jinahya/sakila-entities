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
import javax.persistence.FetchType;
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

/**
 * An entity class for binding to/from {@value #TABLE_NAME} table.
 * <blockquote>
 * The country table contains a list of countries.
 * <p>
 * The country table is referred to by a foreign key in the {@link City city} table.
 * </blockquote>
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-country.html">The country Table (Sakila
 * Sample Database, MySQL Documentation)</a>
 */
@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false))
@Entity
@Table(name = TABLE_NAME)
public class Country extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of this entity. The value is {@value}.
     */
    public static final String TABLE_NAME = "country";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The primary key column name of this entity. The value is {@value}.
     * <blockquote>
     * A surrogate primary key used to uniquely identify each country in the table.
     * </blockquote>
     * {@code SMALLINT(5) PK NN UN AI}
     */
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The database column name for {@link #ATTRIBUTE_NAME_COUNTRY} attribute. The value is {@value}.
     * <blockquote>
     * The name of the country.
     * </blockquote>
     * {@code VARCHAR(50) NN}
     */
    public static final String COLUMN_NAME_COUNTRY = "country";

    public static final String ATTRIBUTE_NAME_COUNTRY = "country";

    public static final int SIZE_MIN_COUNTRY = 0; // // TODO: 2019-07-12 empty???

    public static final int SIZE_MAX_COUNTRY = 50;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    public static final String ATTRIBUTE_NAME_CITIES = "cities";

    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    public static final int SIZE_MIN_CITIES = 0;

    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    public static final int SIZE_MAX_CITIES = Integer.MAX_VALUE;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Country() {
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
        return super.toString() + "{"
               + "country=" + country
               + "}";
    }

    // --------------------------------------------------------------------------------------------------------- country

    /**
     * Returns the current value of {@link #ATTRIBUTE_NAME_COUNTRY} attribute.
     *
     * @return the current value of {@link #ATTRIBUTE_NAME_COUNTRY} attribute.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Replaces the current value of {@link #ATTRIBUTE_NAME_COUNTRY} attribute with specified value.
     *
     * @param country new value for {@link #ATTRIBUTE_NAME_COUNTRY} attribute.
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    // ---------------------------------------------------------------------------------------------------------- cities
    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    public Set<City> getCities() {
        if (cities == null) {
            cities = new HashSet<>();
        }
        return cities;
    }

    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
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
    @Column(name = COLUMN_NAME_COUNTRY, nullable = false, length = SIZE_MAX_COUNTRY)
    @NamedAttribute(ATTRIBUTE_NAME_COUNTRY)
    private String country;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-12 remove!!!
    @Deprecated // forRemoval = true
    @Size(min = SIZE_MIN_CITIES, max = SIZE_MAX_CITIES)
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = City.ATTRIBUTE_NAME_COUNTRY, orphanRemoval = false,
               targetEntity = City.class)
    @NamedAttribute(ATTRIBUTE_NAME_CITIES)
    private Set<@NotNull City> cities;
}
