package com.github.jinahya.mysql.sakila.persistence.converter;

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

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * An abstract class for converting enum values to database column values.
 *
 * @param <E> enum type parameter
 * @param <T> column type parameter
 */
public abstract class EnumAttributeConverter<E extends Enum<E>, T> extends AbstractAttributeConverter<E, T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * @param attributeConverter {@inheritDoc}
     * @param columnConverter    {@inheritDoc}
     * @param enumClass          The class of {@link E}.
     * @see #attributeConverter
     * @see #columnConverter
     */
    public EnumAttributeConverter(final Function<? super E, ? extends T> attributeConverter,
                                  final Function<? super T, ? extends E> columnConverter, final Class<E> enumClass) {
        super(attributeConverter, columnConverter);
        this.enumClass = requireNonNull(enumClass, "enumClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected final Class<E> enumClass;
}
