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

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A assert class for {@link Actor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CategoryAssert extends BaseEntityAssert<CategoryAssert, Category> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified actual value.
     *
     * @param actual an actual value.
     */
    CategoryAssert(final Category actual) {
        super(actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    CategoryAssert hasName(@NotBlank final String name) {
        isNotNull().satisfies(a -> assertThat(a.getName()).isNotNull().isEqualTo(name));
        return this;
    }
}
