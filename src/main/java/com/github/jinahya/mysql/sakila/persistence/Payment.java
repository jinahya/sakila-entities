package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "payment";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_PAYMENT_ID = "payment_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";

    public static final String ATTRIBUTE_NAME_CUSTOMER = "customer";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STAFF_ID = "staff_id";

    public static final String ATTRIBUTE_NAME_STAFF = "staff";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_RENTAL_ID = "rental_id";

    public static final String ATTRIBUTE_NAME_RENTAL = "rental";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_AMOUNT = "amount";

    public static final String ATTRIBUTE_NAME_AMOUNT = "amount";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_PAYMENT_DATE = "payment_date";

    public static final String ATTRIBUTE_NAME_PAYMENT_DATE = "paymentDate";

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_CUSTOMER_ID, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_CUSTOMER)
    private Customer customer;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_STAFF_ID, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_STAFF)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME_RENTAL_ID)
    @NamedAttribute(ATTRIBUTE_NAME_RENTAL)
    private Rental rental;

    @Basic(optional = false)
    @Column(name = COLUMN_NAME_AMOUNT, nullable = false, updatable = false, precision = 5, scale = 2)
    @NamedAttribute(ATTRIBUTE_NAME_AMOUNT)
    private BigDecimal amount;

    @PastOrPresent
    @NotNull
    @Basic(optional = false) //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_PAYMENT_DATE, nullable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_PAYMENT_DATE)
    private LocalDateTime paymnetDate;
}
