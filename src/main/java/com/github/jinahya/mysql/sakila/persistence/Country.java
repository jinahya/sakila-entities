package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Country.COLUMN_NAME_COUNTRY_ID;
import static com.github.jinahya.mysql.sakila.persistence.Country.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_COUNTRY_ID, nullable = false))
})
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

    public static final int SIZE_MAX_COUNTRY = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String ATTRIBUTE_NAME_CITIES = "cities";

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = SIZE_MAX_COUNTRY)
    @NotNull
    @Column(name = COLUMN_NAME_COUNTRY, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_COUNTRY)
    private String country;
}
