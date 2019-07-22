package com.github.jinahya.sakila.persistence;

/**
 * A clsas for integration-testing {@link InventoryService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class InventoryServiceIT extends BaseEntityServiceIT<InventoryService, Inventory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    InventoryServiceIT() {
        super(InventoryService.class, Inventory.class);
    }
}
