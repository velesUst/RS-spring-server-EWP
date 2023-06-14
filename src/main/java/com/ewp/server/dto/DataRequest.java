package com.ewp.server.dto;

public class DataRequest {
    private String scale;
    private String series;
    private String wave_id;
    
    public DataRequest() {}

    public DataRequest(String scale, String series, String wave_id) {
        this.scale = scale;
        this.series = series;
        this.wave_id = wave_id;
    }

    public String getScale() {
        return scale;
    }
    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getSeries() {
        return series;
    }
    public void setSeries(String series) {
        this.series = series;
    }

    public String getWave_id() {
        return wave_id;
    }
    public void setWave_id(String wave_id) {
        this.wave_id = wave_id;
    }

}
