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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;

/**
 * An entity clsas for binding {@value #TABLE_NAME} table.
 */
@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = City.COLUMN_NAME_CITY_ID, nullable = false))
@Entity
@Table(name = City.TABLE_NAME)
public class City extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of this entity class. The value is {@value}.
     */
    public static final String TABLE_NAME = "city";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CITY_ID = "city_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CITY = "city";

    public static final String ATTRIBUTE_NAME_CITY = "city";

    public static final int SIZE_MIN_CITY = 0;

    public static final int SIZE_MAX_CITY = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    public static final String ATTRIBUTE_NAME_COUNTRY = "country";

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-10 remove this field!
    @Deprecated
    public static final String ATTRIBUTE_NAME_ADDRESSES = "addresses";

    // TODO: 2019-07-10 remove this field!
    @Deprecated
    public static final int SIZE_MIN_ADDRESSES = 0;

    // TODO: 2019-07-10  remove this field!
    @Deprecated
    public static final int SIZE_MAX_ADDRESSES = Integer.MAX_VALUE;

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
        return Objects.equals(getCity(), that.getCity())
               && Objects.equals(getCountry(), that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCountry());
    }

    // ------------------------------------------------------------------------------------------------------------ city
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    // --------------------------------------------------------------------------------------------------------- country
    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        if (this.country != null) {
            final boolean removedFromOldCountry = this.country.getCities().remove(this);
        }
        this.country = country;
        if (this.country != null && !this.country.getCities().contains(this)) {
            final boolean addedToNewCountry = this.country.addCity(this);
        }
    }

    // ------------------------------------------------------------------------------------------------------- addresses
    // TODO: 2019-07-10 remove this method!
    @Deprecated
    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        return addresses;
    }

    // TODO: 2019-07-10 remove this method!
    @Deprecated
    public void addAddress(final Address address) {
        getAddresses().add(address);
        if (address.getCity() != this) {
            address.setCity(this);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_CITY, max = SIZE_MAX_CITY)
    @NotNull // TODO: 2019-07-10 how about @NotBlank?
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CITY, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CITY)
    private String city;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_COUNTRY_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_COUNTRY)
    private Country country;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-10 remove this field!
    @Deprecated
    @OneToMany(mappedBy = Address.ATTRIBUTE_NAME_CITY)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESSES)
    private @Size(min = SIZE_MIN_ADDRESSES, max = SIZE_MAX_ADDRESSES) List<@NotNull Address> addresses;
}
