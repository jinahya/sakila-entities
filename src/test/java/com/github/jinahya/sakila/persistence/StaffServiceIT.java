package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class StaffServiceIT extends BaseEntityServiceIT<StaffService, Staff> {

    // -----------------------------------------------------------------------------------------------------------------
    StaffServiceIT() {
        super(StaffService.class, Staff.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
