package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

class CategoryServiceIT extends BaseEntityServiceIT<CategoryService, Category> {

    // -----------------------------------------------------------------------------------------------------------------
    static @PositiveOrZero int firstResult() {
        return firstResult(Category.class);
    }

    static @Positive int maxResults() {
        return maxResults(Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    CategoryServiceIT() {
        super(CategoryService.class, Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
//    void testStreamOrderedByName
}
