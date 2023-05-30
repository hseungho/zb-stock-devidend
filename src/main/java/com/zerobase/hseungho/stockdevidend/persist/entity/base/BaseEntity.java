package com.zerobase.hseungho.stockdevidend.persist.entity.base;

import java.time.LocalDateTime;

public interface BaseEntity {

    Long getId();
    LocalDateTime getCreatedAt();

}
