package com.ewp.server.persistenceR2DB.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import lombok.Builder;


@Builder(toBuilder = true)
@Table(value = "partitioned")
public class Partitioned {
    @Id
    private Long id;

    @Column("candlestick_id")
    private Long candlestick_id;

    @Transient
    private Candlestick candlestick;  

    
    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    
    public Long getCandlestick_id() {
        return candlestick_id;
    }
    public void setCandlestick_id(final Long candlestick_id) {
        this.candlestick_id = candlestick_id;
    }

}