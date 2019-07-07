package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Rental.COLUMN_NAME_RENTAL_ID;
import static com.github.jinahya.mysql.sakila.persistence.Rental.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID,
                column = @Column(name = COLUMN_NAME_RENTAL_ID, nullable = false))
})
@Entity
@Table(name = TABLE_NAME)
public class Rental extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "rental";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_RENTAL_ID = "rental_id";

    // -----------------------------------------------------------------------------------------------------------------

    public static final String COLUMN_NAME_RENTAL_DATE = "rental_date";

    public static final String ATTRIBUTE_NAME_RENTAL_DATE = "rentalDate";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_INVENTORY_ID = "inventory_id";

    public static final String ATTRIBUTE_NAME_INVENTORY = "inventory";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";

    public static final String ATTRIBUTE_NAME_CUSTOMER = "customer";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_RETURN_DATE = "return_date";

    public static final String ATTRIBUTE_NAME_RETURN_DATE = "returnDate";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STAFF_ID = "staff_id";

    public static final String ATTRIBUTE_NAME_STAFF = "staff";

    // -----------------------------------------------------------------------------------------------------------------
    //@Past
    @NotNull
    @Basic(optional = false) // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_RENTAL_DATE, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_RENTAL_DATE)
    private LocalDateTime rentalDate;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_INVENTORY_ID, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_INVENTORY)
    private Inventory inventory;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_CUSTOMER_ID, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CUSTOMER)
    private Customer customer;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RETURN_DATE, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_RETURN_DATE)
    private LocalDateTime returnDate;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_STAFF_ID, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_RETURN_DATE)
    private Staff staff;
}
