package com.ewp.server.persistenceR2DB.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Table(value = "candlestick")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Candlestick {
    @Id
    private Long id;

    @Column("series_id")
    private Long series_id;

    @Column("scale_id")
    private Long scale_id;

    private LocalDateTime ticktime;

    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    
    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    
    public Long getSeries_id() {
        return series_id;
    }
    public void setSeries_id(final Long series_id) {
        this.series_id = series_id;
    }

    public Long getScale_id() {
        return scale_id;
    }
    public void setScale_id(final Long scale_id) {
        this.scale_id = scale_id;
    }

    public LocalDateTime getTicktime() {
        return ticktime;
    }
    public void setTicktime(final LocalDateTime ticktime) {
        this.ticktime = ticktime;
    }

    public double getOpen() {
        return open;
    }
    public void setOpen(final double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }
    public void setHigh(final double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }
    public void setLow(final double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }
    public void setClose(final double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }
    public void setVolume(final double volume) {
        this.volume = volume;
    }

}