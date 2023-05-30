package com.zerobase.hseungho.stockdevidend.persist.entity;

import com.zerobase.hseungho.stockdevidend.model.Dividend;
import com.zerobase.hseungho.stockdevidend.persist.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@ToString
@NoArgsConstructor
public class DividendEntity extends BaseUpdatableEntity {

    private Long companyId;
    private LocalDateTime date;
    private String dividend;

    private DividendEntity(Long companyId, LocalDateTime date, String dividend) {
        this.companyId = companyId;
        this.date = date;
        this.dividend = dividend;
    }

    public static DividendEntity of(Long companyId, Dividend dividend) {
        return new DividendEntity(
                companyId,
                dividend.getDate(),
                dividend.getDividend()
        );
    }

}
