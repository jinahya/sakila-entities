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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.sakila.persistence.Store.COLUMN_NAME_STORE_ID;
import static com.github.jinahya.sakila.persistence.Store.TABLE_NAME;

@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_STORE_ID))
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
    // TODO: 2019-07-10 Remove this field.
    @Deprecated
    public static final String ATTRIBUTE_NAME_STAFFS = "staffs";

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-10 Remove this field.
    @Deprecated
    public static final String ATTRIBUTE_NAME_CUSTOMERS = "customers";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Store() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + "{"
               + "manager=" + manager
               + ",address=" + address
               + "}";
    }

    // TODO: 2019-07-10 Consider adding equals/hashCode.

    // --------------------------------------------------------------------------------------------------------- manager
    public Staff getManager() {
        return manager;
    }

    public void setManager(final Staff manager) {
        this.manager = manager;
    }

    // --------------------------------------------------------------------------------------------------------- address
    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-10 Remove this method.
    @Deprecated
    public Set<Staff> getStaffs() {
        if (staffs == null) {
            staffs = new HashSet<>();
        }
        return staffs;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-10 Remove this method.
    @Deprecated
    public Set<Customer> getCustomers() {
        if (customers == null) {
            customers = new HashSet<>();
        }
        return customers;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_MANAGER_STAFF_ID, nullable = false, unique = true)
    @NamedAttribute(ATTRIBUTE_NAME_MANAGER)
    private Staff manager;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS)
    private Address address;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-10 Remove this field.
    @Deprecated
    @OneToMany(mappedBy = Staff.ATTRIBUTE_NAME_STORE)
    @NamedAttribute(ATTRIBUTE_NAME_STAFFS)
    private Set<Staff> staffs;

    // TODO: 2019-07-10 Remove this field.
    @Deprecated
    @OneToMany(mappedBy = Customer.ATTRIBUTE_NAME_STORE)
    @NamedAttribute(ATTRIBUTE_NAME_STAFFS)
    private Set<Customer> customers;
}
