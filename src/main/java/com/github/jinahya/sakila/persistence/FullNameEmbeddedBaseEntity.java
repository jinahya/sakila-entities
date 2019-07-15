package com.github.jinahya.sakila.persistence;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Deprecated
abstract class FullNameEmbeddedBaseEntity extends BaseEntity implements FullNamed {

    // -----------------------------------------------------------------------------------------------------------------

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(final FullName fullName) {
        this.fullName = fullName;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @NotNull
    @Embedded
    private FullName fullName;
}
