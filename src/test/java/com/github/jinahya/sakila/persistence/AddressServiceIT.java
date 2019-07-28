package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

/**
 * A class for integration-testing {@link AddressService} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class AddressServiceIT extends BaseEntityServiceIT<AddressService, Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressServiceIT() {
        super(AddressService.class, Address.class);
    }
}
