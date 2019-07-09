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

import javax.persistence.AttributeConverter;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * An abstract class for converting enum values to database column values.
 *
 * @param <X> the type of the entity attribute
 * @param <Y> the type of the database column
 */
public abstract class AbstractAttributeConverter<X, Y> implements AttributeConverter<X, Y> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified converters.
     *
     * @param attributeConverter a converter for converting an attribute value to a column value.
     * @param columnConverter    a function for converting a column value to an attribute value.
     * @see #attributeConverter
     * @see #columnConverter
     */
    public AbstractAttributeConverter(final Function<? super X, ? extends Y> attributeConverter,
                                      final Function<? super Y, ? extends X> columnConverter) {
        super();
        this.attributeConverter = requireNonNull(attributeConverter, "attributeConverter is null");
        this.columnConverter = requireNonNull(columnConverter, "columnConverter is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public Y convertToDatabaseColumn(final X attribute) {
        return ofNullable(attribute).map(attributeConverter).orElse(null);
    }

    @Override
    public X convertToEntityAttribute(final Y column) {
        return ofNullable(column).map(columnConverter).orElse(null);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The converter for converting an attribute to a db data.
     */
    protected final Function<? super X, ? extends Y> attributeConverter;

    /**
     * The converter for converting from db data to an attribute.
     */
    protected final Function<? super Y, ? extends X> columnConverter;
}
