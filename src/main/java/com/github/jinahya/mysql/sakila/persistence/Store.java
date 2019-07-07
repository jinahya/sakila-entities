package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Store.COLUMN_NAME_STORE_ID;
import static com.github.jinahya.mysql.sakila.persistence.Store.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_STORE_ID))
})
@Entity
@Table(name = TABLE_NAME)
public class Store extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "store";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STORE_ID = "store_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_MANAGER_STAFF_ID = "manager_staff_id";

    public static final String ATTRIBUTE_NAME_MANAGER = "manager";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    public static final String ATTRIBUTE_NAME_ADDRESS = "address";

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_MANAGER_STAFF_ID, nullable = false, unique = true)
    @NamedAttribute(ATTRIBUTE_NAME_MANAGER)
    private Staff manager;

    @NotNull
    @ManyToOne(optional = false)
    @Column(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS)
    private Address address;
}
