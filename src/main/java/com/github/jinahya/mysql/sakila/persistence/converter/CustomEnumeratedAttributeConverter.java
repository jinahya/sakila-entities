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

public abstract class CustomEnumeratedAttributeConverter<E extends Enum<E> & CustomEnumerated<E, T>, T>
        extends EnumAttributeConverter<E, T> {

    public CustomEnumeratedAttributeConverter(final Class<E> enumClass) {
        super(
                CustomEnumerated::getDatabaseColumn,
                t -> {
                    // assert t != null;
                    if (t == null) {
                        return null;
                    }
                    for (final E enumConstant : enumClass.getEnumConstants()) {
                        if (t.equals(enumConstant.getDatabaseColumn())) {
                            return enumConstant;
                        }
                    }
                    throw new IllegalArgumentException("no constant for column value: " + t);
                },
                enumClass
        );
    }
}
