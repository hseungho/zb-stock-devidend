package com.zerobase.hseungho.stockdevidend.persist.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zerobase.hseungho.stockdevidend.model.Dividend;
import com.zerobase.hseungho.stockdevidend.persist.entity.base.BaseUpdatableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity(name = "DIVIDEND")
@Getter
@ToString
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = { "companyId", "date" } // unique key 설정을 통해 특정 필드의 값이 모두 중복되지 않도록 설정할 수 있음.
                )
        }
)
public class DividendEntity extends BaseUpdatableEntity {

    private Long companyId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
