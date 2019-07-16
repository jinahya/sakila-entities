package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * jsonrpc-bind
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

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Constants and utilities for Bean-Validation.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public final class BeanValidationTests {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A validation factory.
     */
    // https://stackoverflow.com/a/54750045/330457
    static final ValidatorFactory VALIDATION_FACTORY
            = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Applies a validator to specified function and returns the result.
     *
     * @param function the function to be applied.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    static <R> R applyValidator(final Function<? super Validator, ? extends R> function) {
        return requireNonNull(function, "function is null").apply(VALIDATION_FACTORY.getValidator());
    }

    /**
     * Applies a validator to specified along with a second argument supplied by specified supplier.
     *
     * @param function the function to be applied.
     * @param supplier the supplier for the second argument of the {@code function}.
     * @param <U>      second argument type parameter
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    static <U, R> R applyValidator(final BiFunction<? super Validator, ? super U, ? extends R> function,
                                   final Supplier<? extends U> supplier) {
        if (function == null) {
            throw new NullPointerException("function is null");
        }
        if (supplier == null) {
            throw new NullPointerException("supplier is null");
        }
        return applyValidator(v -> function.apply(v, supplier.get()));
    }

    /**
     * Accepts a validator to specified consumer.
     *
     * @param consumer the consumer to be accepted with a validator.
     */
    static void acceptValidator(final Consumer<? super Validator> consumer) {
        if (consumer == null) {
            throw new NullPointerException("consumer is null");
        }
        applyValidator(v -> {
            consumer.accept(v);
            return null;
        });
    }

    /**
     * Accepts a validator to specified consumer along with a second argument supplied by specified supplier.
     *
     * @param consumer the consumer to be accepted.
     * @param supplier the supplier for the second argument of the {@code consumer}.
     * @param <U>      second argument type parameter
     */
    static <U> void acceptValidator(final BiConsumer<? super Validator, ? super U> consumer,
                                    final Supplier<? extends U> supplier) {
        if (consumer == null) {
            throw new NullPointerException("consumer is null");
        }
        if (supplier == null) {
            throw new NullPointerException("supplier is null");
        }
        acceptValidator(v -> consumer.accept(v, supplier.get()));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Validates specified object.
     *
     * @param object the object to be validated.
     * @param <T>    object type paraamter
     * @return a set of constraint violations; empty if valid.
     */
    static <T> Set<ConstraintViolation<T>> validate(final T object) {
        if (object == null) {
            throw new NullPointerException("object is null");
        }
        return applyValidator(v -> v.validate(object));
    }

    /**
     * Checks whether specified object is valid.
     *
     * @param object the object to be checked.
     * @return true if {@code object} is valid; {@code false} if not.
     */
    static boolean isValid(final Object object) {
        return validate(object).isEmpty();
    }

    /**
     * Checks if specified object is valid. A {@link ConstraintViolationException} will be thrown if specified object is
     * not valid.
     *
     * @param object the object to be checked.
     * @param <T>    object type parameter
     * @return given {@code object}.
     */
    static <T> T requireValid(final T object) {
        final Set<ConstraintViolation<T>> violations = validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return object;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    private BeanValidationTests() {
        super();
    }
}
