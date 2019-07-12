package com.github.jinahya.sakila.persistence;

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@ExtendWith({WeldJunit5Extension.class})
public class ActorParameterResolver implements ParameterResolver {

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Actor.class;
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private EntityManager entityManager;
}
