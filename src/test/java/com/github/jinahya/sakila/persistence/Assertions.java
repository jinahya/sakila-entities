package com.github.jinahya.sakila.persistence;

class Assertions {

    // -----------------------------------------------------------------------------------------------------------------
    static FullNamedAssert assertThat(final Actor actual) {
        return new ActorAssert(actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Assertions() {
        super();
    }
}
