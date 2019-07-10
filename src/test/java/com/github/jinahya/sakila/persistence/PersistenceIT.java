package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({WeldJunit5Extension.class})
@Slf4j
class PersistenceIT {

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void assertEntityManagerInjected() {
        assertNotNull(entityManager);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    @PersistenceContext
    private EntityManager entityManager;
}
