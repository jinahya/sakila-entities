package com.github.jinahya.mysql.sakila.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for matching the annotated field name to specified attribute value.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NamedAttribute {

    /**
     * The target attribute name to match.
     *
     * @return target attribute name to match.
     */
    String value();
}
