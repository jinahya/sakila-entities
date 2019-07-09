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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.City.COLUMN_NAME_CITY_ID;
import static com.github.jinahya.mysql.sakila.persistence.City.TABLE_NAME;

@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_CITY_ID, nullable = false))
@Entity
@Table(name = TABLE_NAME)
public class City extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "city";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CITY_ID = "city_id";

    //public static final String ATTRIBUTE_NAME_CITY_ID = "cityId";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CITY = "city";

    public static final String ATTRIBUTE_NAME_CITY = "city";

    public static final int SIZE_MIN_CITY = 0;

    public static final int SIZE_MAX_CITY = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    public static final String ATTRIBUTE_NAME_COUNTRY = "country";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public City() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "city=" + city
               + ",country=" + country
               + "}";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof City)) {
            return false;
        }
        final City that = (City) obj;
        return Objects.equals(getCity(), that.getCity()) && Objects.equals(getCountry(), that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCountry());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        if (this.country != null) {
            final boolean removed = this.country.getCities().remove(this);
        }
        this.country = country;
        if (!this.country.getCities().contains(this)) {
            final boolean added = this.country.getCities().add(this);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_CITY, max = SIZE_MAX_CITY)
    @NotBlank
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CITY, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CITY)
    private String city;

    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_COUNTRY)
    private Country country;
}
