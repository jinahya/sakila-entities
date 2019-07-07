package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Inventory.COLUMN_NAME_INVENTORY_ID;
import static com.github.jinahya.mysql.sakila.persistence.Inventory.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID,
                column = @Column(name = COLUMN_NAME_INVENTORY_ID, nullable = false))
})
@Entity
@Table(name = TABLE_NAME)
public class Inventory extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "inventory";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_INVENTORY_ID = "inventory_id";

    // -----------------------------------------------------------------------------------------------------------------

    // TODO: 2019-07-07 finish!
}
