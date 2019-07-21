package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

/**
 * A class for testing {@link Staff} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class StaffTest extends BaseEntityTest<Staff> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    StaffTest() {
        super(Staff.class);
    }
}
