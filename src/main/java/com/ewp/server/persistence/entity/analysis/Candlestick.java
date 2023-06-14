package com.ewp.server.persistence.entity.analysis;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "candlestick")
public class Candlestick {
    @Id
    private Long id;

    @Column(name = "series_id")
    private Long series_id;

    @Column(name = "scale_id")
    private Long scale_id;

    private LocalDateTime ticktime;

    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public Candlestick() {
        super();
    }

    public Candlestick(final Long id) {
        super();
        this.id = id;
    }

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