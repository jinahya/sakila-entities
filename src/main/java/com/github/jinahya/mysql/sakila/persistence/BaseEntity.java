package com.github.jinahya.mysql.sakila.persistence;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String ATTRIBUTE_NAME_ID = "id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LAST_UPDATE = "last_update";

    public static final String ATTRIBUTE_NAME_LAST_UPDATE = "lastUpdate";

    // -----------------------------------------------------------------------------------------------------------------
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @NamedAttribute(ATTRIBUTE_NAME_ID)
    private Integer id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_LAST_UPDATE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_UPDATE)
    private Date lastUpdate;
}
