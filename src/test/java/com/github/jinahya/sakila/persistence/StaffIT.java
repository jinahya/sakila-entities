package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
class StaffIT extends BaseEntityIT<Staff> {

    // -----------------------------------------------------------------------------------------------------------------
    static final long STAFF_COUNT = entityCount(Staff.class);

    // -----------------------------------------------------------------------------------------------------------------
    static Staff randomStaff() {
        return randomEntity(Staff.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    StaffIT() {
        super(Staff.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void testGetPictureFormatName() throws IOException {
        final String pictureFormatName = randomEntity().getPictureFormatName();
        log.debug("pictureFormatName: {}", pictureFormatName);
    }
}
