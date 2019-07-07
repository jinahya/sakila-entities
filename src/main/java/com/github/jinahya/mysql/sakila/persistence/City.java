package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.City.COLUMN_NAME_CITY_ID;
import static com.github.jinahya.mysql.sakila.persistence.City.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_CITY_ID, nullable = false))
})
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

    public static final int SIZE_MAX_CITY = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_COUNTRY_ID = "country_id";

    public static final String ATTRIBUTE_NAME_COUNTRY = "country";

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = SIZE_MAX_CITY)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CITY, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CITY)
    private String city;

    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_COUNTRY)
    private Country country;
}
