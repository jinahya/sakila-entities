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
import static com.github.jinahya.mysql.sakila.persistence.Customer.COLUMN_NAME_CUSTOMER_ID;
import static com.github.jinahya.mysql.sakila.persistence.Customer.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_CUSTOMER_ID))
})
@Entity
@Table(name = TABLE_NAME)
public class Customer extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "customer";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STORE_ID = "store_id";

    public static final String ATTRIBUTE_NAME_STORE = "store";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    public static final String ATTRIBUTE_NAME_FIRST_NAME = "firstName";

    public static final int SIZE_MAX_FIRST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static final String ATTRIBUTE_NAME_LAST_NAME = "lastName";

    public static final int SIZE_MAX_LAST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMAIL = "email";

    public static final String ATTRIBUTE_NAME_EMAIL = "email";

    public static final int SIZE_MAX_EMAIL = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    public static final String ATTRIBUTE_NAME_ADDRESS = "address";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ACTIVE = "active";

    public static final String ATTRIBUTE_NAME_ACTIVE = "active";

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_STORE_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_STORE)
    private Store store;

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

    @Size(max = SIZE_MAX_EMAIL)
    @Basic
    @Column(name = COLUMN_NAME_EMAIL)
    @NamedAttribute(ATTRIBUTE_NAME_EMAIL)
    private String email;

    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS)
    private Address address;

    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ACTIVE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ACTIVE)
    private boolean active;
}
