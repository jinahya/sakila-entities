package com.github.jinahya.sakila.persistence;

class CategoryIT extends BaseEntityIT<Category> {

    // -----------------------------------------------------------------------------------------------------------------
    static final int CATEGORY_COUNT = entityCountAsInt(Category.class);

    static Category randomCategory() {
        return randomEntity(Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    CategoryIT() {
        super(Category.class);
    }
}
