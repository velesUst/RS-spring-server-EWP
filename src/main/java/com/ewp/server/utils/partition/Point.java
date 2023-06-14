package com.ewp.server.utils.partition;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.ewp.server.persistenceR2DB.entity.Candlestick;

public class Point {
    public long x;
    public double y;

    public double temp_val;
    public int temp_pos;

    public Candlestick cs;

public Point(){}
    public Point(Candlestick cs_, int pos) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(cs_.getTicktime(), ZoneId.systemDefault());
        this.x = zonedDateTime.toInstant().toEpochMilli();;
        this.y = cs_.getOpen();
        this.temp_pos = pos;

        this.cs = cs_;
    }
}